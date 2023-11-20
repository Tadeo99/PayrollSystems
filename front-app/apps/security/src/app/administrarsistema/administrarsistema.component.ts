import {Component, EventEmitter,Optional, OnInit,OnChanges,SimpleChanges,AfterViewInit} from '@angular/core';
import {FormControl} from '@angular/forms';
import { Router,ActivatedRoute } from '@angular/router';
import {TranslateService} from '@ngx-translate/core';

import {CommonServiceImpl} from "../../common/common.impl.service";
import {LoginService} from "../../seguridad/login/login.service";
import {TypeSelectItemService} from "../../../typeselectitemservice/typeselectitem.service";

import {BaseComponent,DialogConfirmContent,DialogContent} from "../../../base/base.component";
import {MatDialog,MatDialogRef} from '@angular/material/dialog';
import {MatSnackBar} from '@angular/material/snack-bar';

import {AdministrarSistemaService} from "./administrarsistema.service";
import {Properties} from "../../../models/seguridad/properties.model";
import { BaseDialogContent } from '../../../base/base.dialog.content.component';

/**
 * La Class AdministrarSistemaComponent.
 * <ul>
 * <li>Copyright 2017 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Mon Oct 16 14:31:03 COT 2017
 * @since SIAA-CORE 2.1
 */
 
@Component({
  selector: 'app-administrarsistema',
  templateUrl: './administrarsistema.component.html',
  styleUrls: ['./administrarsistema.component.css'],
  providers: [AdministrarSistemaService]
})
export class AdministrarSistemaComponent extends BaseComponent implements  OnInit, OnChanges,AfterViewInit {
		
	constructor(public dialog: MatDialog, public snackbar: MatSnackBar,router : Router,route:  ActivatedRoute,private propertiesService: AdministrarSistemaService,
	public commonServiceImpl : CommonServiceImpl, public loginDataService : LoginService,public _typeSelectItemService : TypeSelectItemService,public _translate: TranslateService) { 
		super(dialog,snackbar,router,route);
		super.setTypeSelectItemService(_typeSelectItemService);
		super.setTraslate(_translate);
		//this.debounceTimeProcesar().subscribe(term =>  {this.search = term; this.buscar()});
		super.setLoginDataService(loginDataService);
	}
	
	ngAfterViewInit() {
    // viewChild is set after the view has been initialized
	}

    ngOnChanges(changes: SimpleChanges) { 
		
		this.showAccion();
    }
	
	ngOnInit() {
		this.onInit();
		this.inicializar();
	}
	 
	onInit() {
    /*var id = this.route.params.subscribe(params => {
      var id = params['id'];

    });*/
    }
  
	/**
	 * Inicializar.
	 *
	 */
	private inicializar() {
		super.validarPaginaView();
		super.getUsuarioSession();
	}
}

@Component({
	template: `
	<app-sistema *ngIf="esModalSistema" [isCrud] = "isCrud" [id] = "id" [esIncludeComponent] = "esIncludeComponent"  [showModal]="showModal" [showSelectMultiple] = "showSelectMultiple" [dialogRef] = "dialogRef" [data] = "data"></app-sistema>
	<app-usuario *ngIf="esModalUsuario" [isCrud] = "isCrud" [id] = "id" [esIncludeComponent] = "esIncludeComponent"  [showModal]="showModal" [showSelectMultiple] = "showSelectMultiple" [dialogRef] = "dialogRef" [data] = "data"></app-usuario>
	<app-grupousuario *ngIf="esModalGrupoUsuario" [isCrud] = "isCrud" [id] = "id" [esIncludeComponent] = "esIncludeComponent"  [showModal]="showModal" [showSelectMultiple] = "showSelectMultiple" [dialogRef] = "dialogRef" [data] = "data"></app-grupousuario>
	 `,
  })
  export class DialogContentOverride extends BaseDialogContent {
	public esModalSistema : boolean = false;
	public esModalUsuario : boolean = false;
	public esModalGrupoUsuario : boolean = false;
	constructor(@Optional() public dialogRef: MatDialogRef<DialogContentOverride>) {super() }
  }
  