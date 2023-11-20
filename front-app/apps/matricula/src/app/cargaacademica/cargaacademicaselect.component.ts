import {Component, EventEmitter, OnInit,OnChanges,SimpleChanges,AfterViewInit, Optional, ViewChild} from '@angular/core';
import {FormControl,FormBuilder,Validators, FormArray} from '@angular/forms';
import { Router,ActivatedRoute } from '@angular/router';
import {TranslateService} from '@ngx-translate/core';

import {CommonServiceImpl} from "../../common/common.impl.service";
import {LoginService} from "../../seguridad/login/login.service";
import {TypeSelectItemService} from "../../../typeselectitemservice/typeselectitem.service";

import {BaseComponent,DialogConfirmContent,DialogContent} from "../../../base/base.component";
import {MatDialog,MatDialogRef} from '@angular/material/dialog';
import {MatSnackBar} from '@angular/material/snack-bar';

import {CargaAcademicaService} from "./cargaacademica.service";
import {CargaAcademica} from "../../../models/matricula/cargaacademica.model"; 
import { MatTableDataSource } from '@angular/material/table';
import { SelectionModel } from '@angular/cdk/collections';
import { DetalleCargaAcademica } from '../../../models/matricula/detallecargaacademica.model';
import { DetalleCargaAcademicaService } from '../detallecargaacademica';
import { DetalleCargaAcademicaVO } from '../../../vo/detallecargaacademica.vo'; 
import { MatPaginator } from '@angular/material/paginator';


/**
 * La Class CargaAcademicaComponent.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Sun May 02 02:32:50 COT 2021
 * @since BUILDERP-CORE 2.1
 */
 
@Component({
  selector: 'app-cargaacademicaselect',
  templateUrl: './cargaacademicaselect.component.html',
  styleUrls: ['./cargaacademica.component.css'],
  providers: [CargaAcademicaService, DetalleCargaAcademicaService]
})
export class CargaAcademicaSelectComponent extends BaseComponent implements  OnInit, OnChanges,AfterViewInit {
    
	/** El objeto carga academica. */
    public cargaAcademica : CargaAcademica = new CargaAcademica();
	 
	/** La lista carga academica. */
    public listaCargaAcademica : CargaAcademica[] = [];
    
	/** La lista item select. */
    public listaCargaAcademicaSelectMap : Map<string,CargaAcademica> = new Map<string,CargaAcademica>();

	public mostrasEditT: boolean = false;

	/** detcargaacademica */
	public detCargaAcademica: DetalleCargaAcademica = new DetalleCargaAcademica();

	public idDetPlanEstudioCurso: string = "";
	public personalBydocente: number = 1;
	public personalBydocentePractica: number = 2;

	public dataSource = new MatTableDataSource<DetalleCargaAcademicaVO>();

	public displayedColumns = ['c', 'curso', 'donceteP', 'docenteA', 'grupo', 'A'];
	@ViewChild(MatPaginator) paginator: MatPaginator;

	public listaDetalleCargaAcademicaVO: DetalleCargaAcademicaVO[] = [];

	public listaDetalleCargaAcademicaSelectMap: Map<string, DetalleCargaAcademicaVO> = new Map<string, DetalleCargaAcademicaVO>();

	public detCargaAcademicaAddVO: DetalleCargaAcademicaVO = new DetalleCargaAcademicaVO();

	

	public mostrarBtn: boolean = false;
	
	public gradoTemp:number=null;

	constructor(private fb: FormBuilder,public dialog: MatDialog, public snackbar: MatSnackBar,router : Router,route:  ActivatedRoute,private cargaAcademicaService: CargaAcademicaService,
	protected commonServiceImpl : CommonServiceImpl, public detalleCargaAcademicaService: DetalleCargaAcademicaService,protected loginDataService : LoginService,protected _typeSelectItemService : TypeSelectItemService,protected _translate: TranslateService) { 
		super(dialog,snackbar,router,route);
		super.setTypeSelectItemService(_typeSelectItemService);
		super.setTraslate(_translate);
		this.debounceTimeProcesar().subscribe(term =>  {this.search = term; this.buscar()});
		super.setLoginDataService(loginDataService);
	}
	
	ngAfterViewInit() {
    // viewChild is set after the view has been initialized
	}

    ngOnChanges(changes: SimpleChanges) { 
		if (this.id) {
			this.params = this.params.set('id', this.id + '');
			this.buscar();
		}
		this.showAccion();
    }
	
	ngOnInit() {
		this.onInit();
		this.inicializar();
		this.crearFormulario(this.cargaAcademica);		
	}
	
	private  crearFormulario(obj : CargaAcademica) : void{
		
		this.frmGroup = this.fb.group({
			search: ['']
		  });
		  this.onChange();
	}
	
	private onChange():void{
	 
	}
  
	 
	onInit() {
    /*var id = this.route.params.subscribe(params => {
      var id = params['id'];

    });*/
    }
	get productControlArray() {
		return this.frmGroup.get('listaDetCargaAcademica') as FormArray;
	}

    
	public agregarCursoCargaLectiva() {
		try {
			this.startProgres();
			this.detCargaAcademicaAddVO = this.frmGroup.value;		
			this.detCargaAcademicaAddVO.anhioActivoVO = this.frmGroup.get('anhio').value;
			this.detCargaAcademicaAddVO.idTurno = this.frmGroup.get('itemByTurno.idItem').value;	
			this.detCargaAcademicaAddVO.idEntidad = this.usuarioSession.entidad.idEntidad; 
			this.detCargaAcademicaAddVO.idAula = this.frmGroup.get('aula.idAula').value;

			this.detCargaAcademicaAddVO.listaDetalleCargaAcademicaVO = [];
			if (this.listaDetalleCargaAcademicaSelectMap) {
				this.listaDetalleCargaAcademicaSelectMap.forEach((entryVal : any , entryKey : any )  => {
					this.detCargaAcademicaAddVO.listaDetalleCargaAcademicaVO.push(entryVal);
				})

			}
			this.detalleCargaAcademicaService.agregarCursoCargaLectiva(this.detCargaAcademicaAddVO).subscribe(
				data => {
					if (this.isProcesoOK(data)) {
						this.guardoExito();
						this.buscar();
						this.listaDetalleCargaAcademicaSelectMap = new Map<string, DetalleCargaAcademicaVO>();	
					}
				},
				error => {
					this.mostrarMensajeError(error);
				}, () => {
					this.endProgres();
				});
		} catch (error) {
			this.mostrarMensajeError(error);
		}
		this.endProgres();
	}





	selection = new SelectionModel<DetalleCargaAcademicaVO>(true, []);
	isAllSelected() {
		const numSelected = this.selection.selected.length;
		const numRows = this.dataSource.data.length;
		return numSelected === numRows;
	}
	masterToggle() {
		this.isAllSelected() ?
			this.dataSource.data.forEach(row => {
				if (row.idDetalleCargaAcademica == "") {
					row.idDetalleCargaAcademica = "";
					row.docenteAuxiliar = "";
					row.docentePrincipal = "";
					row.grupo = null;					
					row.check = false;
					this.selection.clear();
					this.listaDetalleCargaAcademicaSelectMap.delete(row.idDetMallaCurricular);
					this.mostrarBtn = false;
				} else {
					this.selection.select(row);
					row.check = true;
					this.mostrarBtn = true;
				}

			}) : this.dataSource.data.forEach(row => {
				this.selection.select(row);
				row.check = true;			
				this.mostrarBtn = true;
			});
	}

	togleCheck(row: DetalleCargaAcademicaVO) {
		console.log("entrada");
		if (row.idDetalleCargaAcademica != "") {
			//this.selection.isSelected(row);
		} else {
			if (row.idDetalleCargaAcademica == "") {
				row.idDetalleCargaAcademica = "";
				row.docenteAuxiliar = "";
				row.docentePrincipal = "";
				row.grupo = null;			
				console.log("Desactivado :- ");
				this.listaDetalleCargaAcademicaSelectMap.delete(row.idDetMallaCurricular);
			}
			//this.selection.isSelected(row);
			this.mostrarBtn = true;
		}

	}
	
	 
	
	/**
	 * Inicializar.
	 *
	 */
	private inicializar() {
		super.validarPaginaView();
		super.getUsuarioSession();
	    this.limpiar();
	}
	
	/**
	 * Limpiar.
	 *
	 */
	private limpiar() {
		try {
			this.listaCargaAcademica = [];
			this.limpiaDataProvider(this.search);
			this.cargaAcademica = new CargaAcademica();
			if (this.frmGroup != null) {
				this.frmGroup.patchValue(this.cargaAcademica,{onlySelf: true, emitEvent: false });
			}
			this.buscar();
		} catch (e) {
			this.mostrarMensajeError(e);
		}
	}
	
	/**
	 * eliminar.
	 *
	 */
	private eliminar ( id : any ) {
		try {
			  this.cargaAcademicaService.eliminar(id).subscribe(
			    data => {
					if (this.isProcesoOK(data)) {
						this.eliminoExito();
						this.buscar();
					}
				},
				error => {
					this.mostrarMensajeError(error);
			   }
			 );
		} catch (e) {
			this.mostrarMensajeError(e);
		}
	}	 
	 
	/**
	 * confirmar eliminar.
	 *
	 */	 
	public confirmarEliminar(cargaAcademicaTemp : CargaAcademica) {
		this.cargaAcademica = cargaAcademicaTemp;
		let dialogRef = this.dialog.open(DialogConfirmContent);
		dialogRef = this.abrirModalConfirDialogEliminar(dialogRef);
		dialogRef.afterClosed().subscribe(result => {
		 if (result){
			 this.eliminar(this.cargaAcademica.idCargaAcademica);
		 }
		});
    }
 
	/* 
	asociar 
	*/
	public asociar(cargaAcademicaTemp : CargaAcademica) {
		try {
			if (this.showModal && !this.showSelectMultiple) {
				cargaAcademicaTemp.checked = true;
				this.agregarCheck(cargaAcademicaTemp);
				this.dialogRef.close(this.listaCargaAcademicaSelectMap);
			}
		} catch (e) {
			this.mostrarMensajeError(e);
		}	
	}	
	/**
	 * Buscar.
	 *
	 */
	public buscar() {
		try {
			this.listaCargaAcademica = [];
			this.mostrarPanelForm = false;
			this.limpiaDataProvider(this.search);
			this.cargaAcademicaService.paginador(this.dataProvider, this.params).subscribe (
			data => {
				if (this.isProcesoOK(data)) {
					this.setDataProvider(data);
					this.listaCargaAcademica = data.listaResultado;
					this.asociarData();
					this.mostrarPanelForm = false;
					this.noEncontroRegistoAlmanecado(this.dataProvider);				
				}
			}, 
			error => {
				this.mostrarMensajeError(error);
			}
			);
		} catch (e) {
			this.mostrarMensajeError(e);
		}
	}
	
	private asociarData() : void {
		if (this.id != null && this.id != '' && this.listaCargaAcademica.length == 1) {
            this.asociar(this.listaCargaAcademica[0]);
		}
	}

		
	/**
	 * Buscar paginado.
	 */
	private buscarPaginado() {
		if (this.dataProvider.totalResults > 0) {
			 this.calcularStartRow();
			 this.cargaAcademicaService.paginador(this.dataProvider, this.params).subscribe(
				data => {
					if (this.isProcesoOK(data)) {
						this.listaCargaAcademica = data.listaResultado;
						this.asociarData();
						if (this.showSelectMultiple) {
							this.verificarCheck();
						}
					}
				},
				error => {
					this.mostrarMensajeError(error);
				}
			 );
	   }
	}
	
  /**
	 * getBufferedData.
	 *
   */
   public getBufferedData(event : any) {
	this.dataProvider = event.dataProvider;
    this.buscarPaginado();
   }
	
   /**
	 * cancelar.
	 *
   */
   public cancelar() {
	this.mostrarPanelForm = false;
   }
	
  private lanzarCargaAcademica(){
    // Usamos el mÃ©todo emit
    this.change.emit({cargaAcademica: this.cargaAcademica});
  }
  
   /*
   agregar check
   */
  public agregarCheck(cargaAcademicaTemp : CargaAcademica) {
     if (cargaAcademicaTemp.checked) {
		 if (!this.listaCargaAcademicaSelectMap.has(cargaAcademicaTemp.idCargaAcademica)) {
			this.listaCargaAcademicaSelectMap.set(cargaAcademicaTemp.idCargaAcademica,cargaAcademicaTemp);
		 }
	 } else {
		if ((this.listaCargaAcademicaSelectMap.has(cargaAcademicaTemp.idCargaAcademica))) {
			this.listaCargaAcademicaSelectMap.delete(cargaAcademicaTemp.idCargaAcademica);
		}
	 }
  }
  /*
   verificar check
  */
  private verificarCheck() {
	this.listaCargaAcademica.forEach((data) => {
       if ((this.listaCargaAcademicaSelectMap.has(data.idCargaAcademica))) {
			data.checked = true;
	   }
    });
  }
   
}

 