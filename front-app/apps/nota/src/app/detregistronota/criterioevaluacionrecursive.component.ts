import { Component, EventEmitter, OnInit, OnChanges, SimpleChanges, AfterViewInit, Input } from '@angular/core';
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
	selector: 'app-criterioevaluacionrecursive',
	templateUrl: './criterioevaluacionrecursive.component.html',
	styleUrls: ['./detregistronota.component.css'],
	providers: [DetRegistroNotaService],
})
export class CriterioEvaluacionRecursiveComponent extends BaseComponent implements OnInit, OnChanges, AfterViewInit {


	@Input("listaCriterioEvaluacionDisponible")
	public listaCriterioEvaluacionDisponible: CriterioEvaluacion[] = [];

	@Input("detRegistroNota")
	public detRegistroNota: DetRegistroNota = new DetRegistroNota();

	@Input("indexTemp")
	public indexTemp :number;

	
	@Input("element")
	public element;

	constructor(private fb: FormBuilder, public dialog: MatDialog, public snackbar: MatSnackBar, router: Router, route: ActivatedRoute, private detRegistroNotaService: DetRegistroNotaService,
		protected commonServiceImpl: CommonServiceImpl, protected loginDataService: LoginService, protected _typeSelectItemService: TypeSelectItemService, protected _translate: TranslateService) {
		super(dialog, snackbar, router, route);
		super.setTypeSelectItemService(_typeSelectItemService);
		super.setTraslate(_translate);
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
		
	}

	ngOnInit() { 
	}
 
}