import { CommonServiceImpl } from "../../common/common.impl.service";
import { LoginService } from "../../seguridad/login/login.service";
import { TypeSelectItemService } from "../../../typeselectitemservice/typeselectitem.service";

import { BaseComponent, DialogConfirmContent, DialogContent } from "../../../base/base.component";
 
import { SelectItemVO } from "../../../vo/selectitem.vo";

import { TipoDocSunatEntidadService } from '../../pago/tipodocsunatentidad/tipodocsunatentidad.service';
import { TipoDocSunatEntidad } from '../../../models/pago/tipodocsunatentidad.model';
import { FacturacionService } from './facturacion.service';
import { Component, OnInit, OnChanges, AfterViewInit, SimpleChanges, ViewChild, OnDestroy } from "@angular/core";
import { Router, ActivatedRoute } from "@angular/router";
import { TranslateService } from "@ngx-translate/core";
import { SunatDatos } from "../../../vo/sunatdatos.vo";  
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { interval } from 'rxjs';
import { ChatService } from '../../chat/chat.service';

/**
 * La Class VentaComponent.
 * <ul>
 * <li>Copyright 2017 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Sat Dec 23 17:16:36 COT 2017
 * @since SIAA-CORE 2.1
 */

@Component({
	selector: 'app-facturacion',
	templateUrl: './facturacion.component.html',
	styleUrls: ['./facturacion.component.css'],
	providers: [FacturacionService, TipoDocSunatEntidadService, ChatService]
})
export class FacturacionComponent extends BaseComponent implements OnInit, OnChanges, AfterViewInit, OnDestroy {

 
	public dataSourceBandeja = new MatTableDataSource<SunatDatos>();

	displayedColumnsBandeja = ['nro', 'nrodoc2', 'tipodoc', 'numerodoc', 'fgenera', 'fenvio', 'situacion', 'observ', 'Ac'];

	itemValor: number = 0;

	isEnviarSuant: boolean = true;

	public listaSunatDatos: SunatDatos[] = [];

	public arrays = {};

	public esGenerarXML: boolean = false;

	public esGenerarXMLAnulado: boolean = false;

	@ViewChild(MatPaginator) paginator: MatPaginator;

	public listaSunatDatosobjetoArray: SunatDatos[] = [];


	public alive: boolean; // used to unsubscribe from the IntervalObservable

	connection;
	connectionInterval;

	constructor(public dialog: MatDialog, public snackbar: MatSnackBar, router: Router, route: ActivatedRoute, public facturacionService: FacturacionService,
		public commonServiceImpl: CommonServiceImpl, public tipoDocSunatEntidadService: TipoDocSunatEntidadService, public loginDataService: LoginService,
		public _typeSelectItemService: TypeSelectItemService, public _translate: TranslateService, private chatService: ChatService) {
		super(dialog, snackbar, router, route);
		super.setTypeSelectItemService(_typeSelectItemService);
		super.setTraslate(_translate);
		super.setLoginDataService(loginDataService);
		this.alive = true;
	}

	ngAfterViewInit() {
		// viewChild is set after the view has been initialized
	}

	ngOnChanges(changes: SimpleChanges) {
		this.showAccion();
	}

	override ngOnDestroy(): void {
		this.alive = false; // switches your IntervalObservable off
		this.connection.unsubscribe();
		this.connectionInterval.unsubscribe();
	}

	ngOnInit() {
		this.alive = true;  
		this.actualizarComprobante(); 
		this.alive = true; 
		this.connectionInterval = interval(1000).subscribe(() => {
			let message2: any = { userName: this.usuarioSession.userName, idUsuario: this.usuarioSession.idUsuario, message: '', esServicioSunat: true };
			this.chatService.sendMessage(message2);
		});
 
		this.onInit();
	}



	onInit() {
		let message: any = { userName: this.usuarioSession.userName, idUsuario: this.usuarioSession.idUsuario, message: '', esServicioSunat: true };
		this.chatService.sendMessage(message);
		this.connection = this.chatService.getMessages().subscribe(message => {
			//console.log("SocketFACTU")
			let lisdaEstado: string[] = [];
			let lisdaSituacionRechazado: string[] = [];
			let lisdaSituacionRechazado2: string[] = [];
			let lisdaEstadoVa: string[] = [];
			this.listaSunatDatos.forEach((listaTemp2) => {
				if (listaTemp2.des_obse == '-') {
					lisdaEstadoVa.push(listaTemp2.des_obse);
				}else if(listaTemp2.ind_situ =='05'){
					lisdaSituacionRechazado2.push(listaTemp2.ind_situ)
				}
			})

			message.listadoSunatDatos.forEach((elemnet) => {
				var objetoArray;
				let body = JSON.stringify(elemnet.listaResultado);
				//console.log("ListadoArray:: " +body)
				let myObjStr = JSON.parse(body);
				objetoArray = JSON.parse(myObjStr);
				console.log("objetoArray " +myObjStr)
				this.listaSunatDatos = objetoArray.listaBandejaFacturador;
			})
			this.listaSunatDatos.forEach((listaTemp) => {
				if (listaTemp.des_obse == '-') {
					lisdaEstado.push(listaTemp.des_obse);
				}else if(listaTemp.ind_situ =='05'){
					lisdaSituacionRechazado.push(listaTemp.ind_situ)
				}
			})
			if (lisdaEstado.length != lisdaEstadoVa.length) {
				this.dataSourceBandeja = new MatTableDataSource<SunatDatos>(this.listaSunatDatos);
				this.dataSourceBandeja.paginator = this.paginator;
			}else if(lisdaSituacionRechazado.length != lisdaSituacionRechazado2.length){
				this.dataSourceBandeja = new MatTableDataSource<SunatDatos>(this.listaSunatDatos);
				this.dataSourceBandeja.paginator = this.paginator;
			}

		})
	}

	generarExtracionXML() {
		this.startProgres()
		let message: any = { userName: this.usuarioSession.userName, idUsuario: this.usuarioSession.idUsuario, message: '', esServicioSunat: true };
		this.chatService.sendMessage(message);
		this.facturacionService.generarExtracionTXT().subscribe(
			data => {
				if (this.isProcesoOK(data)) {
					let myObjStr2
					this.listaSunatDatos = data.listaResultado;
					this.listaSunatDatos.forEach((elemnet) => {
						let body = JSON.stringify(elemnet.listaResultado);
						let myObjStr = JSON.parse(body);
						myObjStr2 = JSON.parse(myObjStr);
					})
					this.dataSourceBandeja = new MatTableDataSource<SunatDatos>(myObjStr2.listaBandejaFacturador);
					this.dataSourceBandeja.paginator = this.paginator;
				}
			},
			error => {
				this.mostrarMensajeError(error);
				this.startProgres();
			},
			() => {
				this.endProgres();
			}
		);
	}

	generarExtracionXMLAnulados() {
		this.startProgres();
		let dialogRef = this.dialog.open(DialogConfirmContent);
		dialogRef = this.abrirModalConfirDialog(dialogRef, "Para enviar los archivos anulados tienes que enviar los XML no anulados", null, null, this.cargarMensaje('modelo.grilla.confirmar.title'));
		dialogRef.afterClosed().subscribe(result => {
			if (result) {
				this.actualizarComprobante()
				this.facturacionService.generarExtracionTXT().subscribe(
					data => {
						if (this.isProcesoOK(data)) {
							let myObjStr2
							this.listaSunatDatos = data.listaResultado;
							this.listaSunatDatos.forEach((elemnet) => {
								let body = JSON.stringify(elemnet.listaResultado);
								let myObjStr = JSON.parse(body);
								myObjStr2 = JSON.parse(myObjStr);
							})
							this.dataSourceBandeja = new MatTableDataSource<SunatDatos>(myObjStr2.listaBandejaFacturador);
							this.dataSourceBandeja.paginator = this.paginator;
						}
					},
					error => {
						this.mostrarMensajeError(error);
						this.startProgres();
					},
					() => {
						this.endProgres();
					}
				);
			}
		});
	}

  
	generarComprobante(temp: SunatDatos) {
		this.facturacionService.generarComprobante(temp).subscribe(
			data => {
				if (this.isProcesoOK(data)) {
					let myObjStr2
					this.listaSunatDatos = data.listaResultado;
					this.listaSunatDatos.forEach((elemnet) => {
						let body = JSON.stringify(elemnet.listaResultado);
						let myObjStr = JSON.parse(body);
						console.log("Array:: " + myObjStr);
						myObjStr2 = JSON.parse(myObjStr);
						this.mensaje = myObjStr2.mensaje;
					})
					this.dataSourceBandeja = new MatTableDataSource<SunatDatos>(myObjStr2.listaBandejaFacturador);
					this.dataSourceBandeja.paginator = this.paginator;
				}
			},
			error => {
				this.mostrarMensajeError(error);
			},
			() => {
				this.endProgres();
			}
		);
	}


	enviarComprobante(temp: SunatDatos) {
		this.facturacionService.enviarComprobante(temp).subscribe(
			data => {
				if (this.isProcesoOK(data)) {
					let myObjStr2
					this.listaSunatDatos = data.listaResultado;
					this.listaSunatDatos.forEach((elemnet) => {
						let body = JSON.stringify(elemnet.listaResultado);
						let myObjStr = JSON.parse(body);
						console.log("Array:: " + myObjStr);
						myObjStr2 = JSON.parse(myObjStr);
						this.mensaje = myObjStr2.mensaje;
					})
					myObjStr2.listaBandejaFacturador.forEach((dataTemp) => {
						if (dataTemp.num_docu == temp.num_docu && dataTemp.fec_gene != null && dataTemp.fec_envi != null) {
							temp.ind_situ = "08";
							temp.fec_gene = dataTemp.fec_gene;
							temp.fec_envi = dataTemp.fec_envi;
							temp.des_obse = dataTemp.des_obse;
						}
						if (dataTemp.ind_situ == "06") {
							if (this.mensaje == "") {
								this.mensaje = dataTemp.des_obse;
							}
							temp.ind_situ = "06";
							temp.des_obse = this.mensaje;
						}
					})
				}
			},
			error => {
				this.mostrarMensajeError(error);
			},
			() => {
				this.endProgres();
			}
		);
	}



	actualizarComprobante() {
		this.startProgres();
		this.facturacionService.actualizarBandeja().subscribe(
			data => {
				if (this.isProcesoOK(data)) {
					let myObjStr2
					this.listaSunatDatos = data.listaResultado;
					if (this.listaSunatDatos.length > 0) {
						console.log(this.listaSunatDatos);
						this.listaSunatDatos.forEach((elemnet) => {
							let body = JSON.stringify(elemnet.listaResultado);
							let myObjStr = JSON.parse(body);
							myObjStr2 = JSON.parse(myObjStr);
							this.mensaje = myObjStr2.mensaje;
						})
						this.dataSourceBandeja = new MatTableDataSource<SunatDatos>(myObjStr2.listaBandejaFacturador);
						this.dataSourceBandeja.paginator = this.paginator;
					}
				}
			},
			error => {
				this.mostrarMensajeError(error);
			},
			() => {
				this.endProgres();
			}
		);
	}



	limpiarBandeja() {
		this.listaSunatDatosobjetoArray = [];
		this.dataSourceBandeja = new MatTableDataSource<SunatDatos>();
		this.dataSourceBandeja.paginator = this.paginator;
		this.listaSunatDatos = [];
		this.facturacionService.eliminarBandeja().subscribe(
			data => {
				if (this.isProcesoOK(data)) {
					this.actualizarComprobante();
				}
			},
			error => {
				this.mostrarMensajeError(error);
			},
			() => {
				this.endProgres();
			}
		);
	}

}

