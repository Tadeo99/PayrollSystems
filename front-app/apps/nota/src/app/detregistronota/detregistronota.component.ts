import { Component, EventEmitter, OnInit, OnChanges, SimpleChanges, AfterViewInit, Input, Optional } from '@angular/core';
import { FormControl, FormBuilder, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';

import { CommonServiceImpl } from "../../common/common.impl.service";
import { LoginService } from "../../seguridad/login/login.service";
import { TypeSelectItemService } from "../../../typeselectitemservice/typeselectitem.service";

import { BaseComponent, DialogConfirmContent, DialogContent } from "../../../base/base.component";
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';

import { DetRegistroNotaService } from "./detregistronota.service";
import { DetRegistroNota } from "../../../models/nota/detregistronota.model";
import { RegistroNota } from "../../../models/nota/registronota.model";
import { DetMatricula } from "../../../models/matricula/detmatricula.model";
import { CursoNotaPeriodo } from '../../../models/nota/cursonotaperiodo.model';
import { CriterioEvaluacion } from '../../../models/matricula/criterioevaluacion.model';
import { MatTableDataSource } from '@angular/material/table';
import { animate, state, style, transition, trigger } from '@angular/animations';
import { DetalleCargaAcademica } from '../../../models/matricula/detallecargaacademica.model';
import { BaseDialogContent } from '../../../base/base.dialog.content.component';
import { DetMallaCurricular } from '../../../models/matricula/detmallacurricular.model';
import { RegistroNotaService } from '../registronota';
import { SelectItemVO } from '../../../vo/selectitem.vo';
import { ListaItemType } from '../../../type/listaitem.type';
import { CursoNotaUnidad } from '../../../models/nota/cursonotaunidad.model';
import { Unidad } from '../../../models/matricula/unidad.model';
import { CursoNotaUnidadProm } from '../../../models/nota/cursonotaunidadprom.model';
/**
 * La Class DetRegistroNotaComponent.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Thu Jun 10 00:13:46 COT 2021
 * @since BUILDERP-CORE 2.1
 */

@Component({
	selector: 'app-detregistronota',
	templateUrl: './detregistronota.component.html',
	styleUrls: ['./detregistronota.component.css'],
	providers: [DetRegistroNotaService, RegistroNotaService],
	animations: [
		trigger('detailExpand', [
			state('collapsed', style({ height: '0px', minHeight: '0' })),
			state('expanded', style({ height: '*' })),
			transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
		]),
		trigger('indicatorRotate', [
			state('collapsed', style({ transform: 'rotate(0deg)' })),
			state('expanded', style({ transform: 'rotate(180deg)' })),
			transition('expanded <=> collapsed',
				animate('225ms cubic-bezier(0.4,0.0,0.2,1)')
			),
		])
	],
})
export class DetRegistroNotaComponent extends BaseComponent implements OnInit, OnChanges, AfterViewInit {

	/** El objeto det registro nota. */
	public detRegistroNota: DetRegistroNota = new DetRegistroNota();

	/** La lista det registro nota. */
	@Input("listaDetRegistroNota")
	public listaDetRegistroNota: DetRegistroNota[] = [];

	/** La lista item select. */
	public listaDetRegistroNotaSelectMap: Map<string, DetRegistroNota> = new Map<string, DetRegistroNota>();

	@Input("cursoNotaUnidadMap")
	public cursoNotaUnidadMap: Map<string, CursoNotaUnidad> = new Map<string, CursoNotaUnidad>();

	@Input("cursoNotaUnidadPromMap")
	public cursoNotaUnidadPromMap: Map<string, CursoNotaUnidadProm> = new Map<string, CursoNotaUnidadProm>();

	@Input("detCargaAcademica")
	public detCargaAcademica: DetalleCargaAcademica = new DetalleCargaAcademica();

	@Input("unidad")
	public unidad: Unidad = new Unidad();

	@Input("listaCriterioEvaluacionDisponible")
	public listaCriterioEvaluacionDisponibleTmp: CriterioEvaluacion[] = [];
	public listaCriterioEvaluacionDisponible: CriterioEvaluacion[] = [];
	public listaCriterioEvaluacionDisponibleEvaluar: CriterioEvaluacion[] = [];
	public ultimoHijoMap: Map<string, string> = new Map<string, string>();

	public dataSourceBandeja = new MatTableDataSource<DetRegistroNota>();

	columnsToDisplay = ['view', 'personal', 'prom1', 'prom2', 'nota'];

	public disabledBotonGuardar: boolean = false;
 

	public NotaLetraMap: Map<Object, string> = new Map<Object, string>();

	constructor(private fb: FormBuilder, public dialog: MatDialog, public snackbar: MatSnackBar, router: Router, route: ActivatedRoute, private detRegistroNotaService: DetRegistroNotaService,
		protected commonServiceImpl: CommonServiceImpl, protected loginDataService: LoginService, protected _typeSelectItemService: TypeSelectItemService,
		protected _translate: TranslateService, private registroNotaService: RegistroNotaService) {
		super(dialog, snackbar, router, route);
		super.setTypeSelectItemService(_typeSelectItemService);
		super.setTraslate(_translate);
		//this.debounceTimeProcesar().subscribe(term => { this.search = term; this.buscar() });
		super.setLoginDataService(loginDataService);
	}

	ngAfterViewInit() {
		// viewChild is set after the view has been initialized
	}

	ngOnChanges(changes: SimpleChanges) {
		if (this.id) {
			this.params = this.params.set('id', this.id + '');
		}
		this.showAccion();
		//this.cargarCombo();
		this.agregarConceptoEvaluar();
		this.listasize = this.listaDetRegistroNota.length;
		this.dataSourceBandeja = new MatTableDataSource<DetRegistroNota>(this.listaDetRegistroNota);
	}

	ngOnInit() {
		this.onInit();
	}
 
	/**
		 * Agregar concepto evaluar.
		 *
		 * @throws Exception the exception
		 */
	public agregarConceptoEvaluar(): void {
		this.listaCriterioEvaluacionDisponibleEvaluar = [];
		this.listaCriterioEvaluacionDisponible = [];
		this.ultimoHijoMap = new Map<string, string>();
		if (this.listaCriterioEvaluacionDisponibleTmp != null) {
			this.listaCriterioEvaluacionDisponibleTmp.forEach((conceptoNotaDis) => {
				if (conceptoNotaDis.criterioEvaluacionHijos.length > 0) {
					this.criterioRecursiveUltimo(conceptoNotaDis.criterioEvaluacionHijos, conceptoNotaDis.idCriterioEvaluacion);
				}
				let data = this.clonarRecursive(conceptoNotaDis);
				this.listaCriterioEvaluacionDisponible.push(data);
			});
			// let promedio = new CriterioEvaluacion();
			// promedio.idCriterioEvaluacion = "PromUnidad";
			// promedio.criterioEvaluacionPadre = new CriterioEvaluacion();
			// promedio.codigo = "Prom.Unidad"
			// //promedio.detMallaCurricular = this.listaCriterioEvaluacionDisponibleTmp[0].detMallaCurricular;
			// this.listaCriterioEvaluacionDisponible.push(promedio);

			// promedio = new CriterioEvaluacion();
			// promedio.idCriterioEvaluacion = "PromBimestral";
			// promedio.criterioEvaluacionPadre = new CriterioEvaluacion();
			// promedio.codigo = "Prom.Bimestral"
			// //promedio.detMallaCurricular = this.listaCriterioEvaluacionDisponibleTmp[0].detMallaCurricular;
			// this.listaCriterioEvaluacionDisponible.push(promedio);
		}

		if (this.listaCriterioEvaluacionDisponible != null) {
			this.listaCriterioEvaluacionDisponible.forEach((conceptoNotaDis) => {
				conceptoNotaDis.checked = false;
				if (conceptoNotaDis.criterioEvaluacionHijos.length == 0) {
					conceptoNotaDis.checked = true;
					this.listaCriterioEvaluacionDisponibleEvaluar.push(conceptoNotaDis);//los si
				}
				if (conceptoNotaDis.criterioEvaluacionHijos.length > 0) {
					let promedioT: CriterioEvaluacion = new CriterioEvaluacion();
					promedioT.idCriterioEvaluacion = conceptoNotaDis.idCriterioEvaluacion;
					promedioT.criterioEvaluacionPadre = new CriterioEvaluacion();
					promedioT.criterioEvaluacionPadre.idCriterioEvaluacion = conceptoNotaDis.idCriterioEvaluacion;
					promedioT.codigo = "Prom."
					conceptoNotaDis.criterioEvaluacionHijos.push(promedioT);
					this.criterioRecursiveEvaluar(conceptoNotaDis.criterioEvaluacionHijos, conceptoNotaDis);
				}
			});
		}


		this.listaDetRegistroNota.forEach((detRegistroNota) => {
			detRegistroNota.cursoNotaEvaluarUnidad = null;
			detRegistroNota.cursoNotaEvaluarUnidad = [];
			let keyProm: string = this.unidad.idUnidad + detRegistroNota.idDetRegistroNota;
			let cursoNotaPromKey: CursoNotaUnidadProm = this.cursoNotaUnidadPromMap.get(keyProm);
			let cursoNotaPromAddPadre: CursoNotaUnidadProm = new CursoNotaUnidadProm();
			if (this.listaCriterioEvaluacionDisponibleEvaluar != null) {
				this.listaCriterioEvaluacionDisponibleEvaluar.forEach((conceptoNotaDis) => {
					let cursoNotaAddPadre: CursoNotaUnidad = new CursoNotaUnidad();
					let key: string = conceptoNotaDis.idCriterioEvaluacion + detRegistroNota.idDetRegistroNota;
					let cursoNotaKey: CursoNotaUnidad = this.cursoNotaUnidadMap.get(key);
					if (cursoNotaKey != null) {
						cursoNotaAddPadre.idCursoNota = (cursoNotaKey.idCursoNota);
						cursoNotaAddPadre.nota = (cursoNotaKey.nota);
					}
					cursoNotaAddPadre.criterioEvaluacion = Object.assign({}, conceptoNotaDis);
					cursoNotaAddPadre.unidad = this.unidad;
					detRegistroNota.cursoNotaEvaluarUnidad.push(cursoNotaAddPadre);
				});
			}

			if (cursoNotaPromKey != null) {
				cursoNotaPromAddPadre.idCursoNotaUnidad = (cursoNotaPromKey.idCursoNotaUnidad);
				cursoNotaPromAddPadre.nota = (cursoNotaPromKey.nota);
			} else {
				cursoNotaPromAddPadre.nota = 0;
			}
			cursoNotaPromAddPadre.unidad = this.unidad.idUnidad;
			detRegistroNota.cursoNotaEvaluarUnidadProm.push(cursoNotaPromAddPadre);
		});
		console.log("this.listaDetRegistroNota ", this.listaDetRegistroNota)
	}

	private criterioRecursiveUltimo(criterioEvaluacionHijos: CriterioEvaluacion[], idPadre: string) {
		criterioEvaluacionHijos.forEach((conceptoNotaDis) => {
			this.ultimoHijoMap.set(idPadre, conceptoNotaDis.idCriterioEvaluacion);
			if (conceptoNotaDis.criterioEvaluacionHijos.length > 0) {
				this.criterioRecursiveUltimo(conceptoNotaDis.criterioEvaluacionHijos, conceptoNotaDis.idCriterioEvaluacion);
			}
		});
	}
	private clonarRecursive(conceptoNotaDis: CriterioEvaluacion): CriterioEvaluacion {
		let data = Object.assign({}, conceptoNotaDis);
		data.criterioEvaluacionHijos = [];
		conceptoNotaDis.criterioEvaluacionHijos.forEach((obj) => {
			let dataHijo = this.clonarRecursive(obj);
			data.criterioEvaluacionHijos.push(dataHijo);
		});
		return data;
	}
	private criterioRecursiveEvaluar(criterioEvaluacionHijos: CriterioEvaluacion[], objCriterioPadre: CriterioEvaluacion) {
		criterioEvaluacionHijos.forEach((conceptoNotaDis) => {
			conceptoNotaDis.checked = true;
			if (conceptoNotaDis.criterioEvaluacionHijos.length == 0) {
				this.listaCriterioEvaluacionDisponibleEvaluar.push(conceptoNotaDis);//los si
				// let idUltimoHijo = this.ultimoHijoMap.get(objCriterioPadre.idCriterioEvaluacion);
				// if (conceptoNotaDis.idCriterioEvaluacion == idUltimoHijo) {
				// 	//this.listaCriterioEvaluacionDisponibleEvaluar.push(objCriterioPadre);//los no padre
				// }
			}
			if (conceptoNotaDis.criterioEvaluacionHijos.length > 0) {
				let promedioT: CriterioEvaluacion = new CriterioEvaluacion();
				promedioT.idCriterioEvaluacion = conceptoNotaDis.idCriterioEvaluacion;
				promedioT.criterioEvaluacionPadre = new CriterioEvaluacion();
				promedioT.criterioEvaluacionPadre.idCriterioEvaluacion = conceptoNotaDis.idCriterioEvaluacion;
				promedioT.codigo = "Prom."
				conceptoNotaDis.criterioEvaluacionHijos.push(promedioT);
				this.criterioRecursiveEvaluar(conceptoNotaDis.criterioEvaluacionHijos, conceptoNotaDis);
			}
		});
	}

	public actualizaFrm(event) {
		if (event.invalid == true || event.invalid == false) {
			this.disabledBotonGuardar = event.invalid;
		}
	}

	public actualizarFormularioPadre(invalid: boolean, temp: CriterioEvaluacion, temp2: DetRegistroNota, index: any) {
		console.log("actualizarFormularioPadre.form.invalid==> " + invalid, index);
		let nota: number = 0;
		let indexNota: number = 0;
		this.listaDetRegistroNota[index].cursoNotaEvaluarUnidad.forEach((obj) => {
			if (obj.criterioEvaluacion.criterioEvaluacionPadre.idCriterioEvaluacion == temp.criterioEvaluacionPadre.idCriterioEvaluacion) {
				if (obj.criterioEvaluacion.codigo != "Prom.") {
					if (obj.nota != null) {
						nota = nota + Number.parseFloat(obj.nota + "");
					}
					indexNota = indexNota + 1
				}
			}
		})
		this.listaDetRegistroNota[index].cursoNotaEvaluarUnidad.forEach((obj) => {
			if (obj.criterioEvaluacion.criterioEvaluacionPadre.idCriterioEvaluacion == temp.criterioEvaluacionPadre.idCriterioEvaluacion) {
				if (obj.criterioEvaluacion.codigo == "Prom.") {
					obj.nota = nota / indexNota;
					obj.nota = Math.round(Number.parseFloat(obj.nota + ""))
				}
			}
		})
		this.disabledBotonGuardar = invalid;
	}


	public regresar() {
		this.change.emit({ isBUscar: true });

	}


	onInit() {
		/*var id = this.route.params.subscribe(params => {
		  var id = params['id'];
	
		});*/
	}

	/**
	 * guardar.
	 *
	 */
	public guardar() {
		// if (this.frmGroup.invalid) {
		// 	this.mostrarMensajeErrorFrmInvalid();
		// 	return;
		// }
		try {
			console.log("this.listaDetRegistroNota ", this.listaDetRegistroNota)
			this.registroNotaService.crear(this.listaDetRegistroNota).subscribe(
				data => {
					if (this.isProcesoOK(data)) {
						this.guardoExito();
						this.change.emit({ isGuardoExito: true, detCargaAcademica: this.detCargaAcademica })
					}
				},
				error => {
					this.mostrarMensajeError(error + " 1");
				}
			);

		} catch (e) {
			this.mostrarMensajeError(e + "2");
		}
	}


	/**
	  * cancelar.
	  *
	*/
	public cancelar() {
		this.mostrarPanelForm = false;
	}


	/**
 El abrir modal criterioEvaluacion. 
*/
	public abrirModalCriterioEvaluacion(pSearch?: string) {
		let dialogRef = this.dialog.open(DialogContentOverride, {
			width: '45%'
		});
		let data: DetMallaCurricular = this.detCargaAcademica.detMallaCurricular;
		// data.id = id;
		dialogRef = this.abrirModal(dialogRef, true, false, false, data, pSearch);
		dialogRef.componentInstance.esModalCriterioEvaluacion = true;
		dialogRef.componentInstance.modulo = 'matricula';
		dialogRef.afterClosed().subscribe(result => {
			if (result) {
				this.change.emit({ isCriterioEvaluacion: true });
			}
		});
	}
}

@Component({
	template: `
	<app-criterioevaluacionmodal *ngIf="esModalCriterioEvaluacion"  [modulo] = "modulo" [id] = "id" [esIncludeComponent] = "esIncludeComponent"  [showModal]="showModal" [showSelectMultiple] = "showSelectMultiple" [dialogRef] = "dialogRef" [data] = "data"></app-criterioevaluacionmodal>
	 `
})
export class DialogContentOverride extends BaseDialogContent {
	public esModalCriterioEvaluacion: boolean = false;
	constructor(@Optional() public dialogRef: MatDialogRef<DialogContentOverride>) { super() }
}