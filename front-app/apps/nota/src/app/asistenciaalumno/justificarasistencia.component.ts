import {Component, Input, OnInit,OnChanges,SimpleChanges,AfterViewInit} from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import {FormControl} from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router,ActivatedRoute } from '@angular/router';
import { BaseComponent } from '../../../base/base.component';
import { AsistenciaAlumno } from '../../../models/nota/asistenciaalumno.model';



/**
 * La Class ListaAsistenciaComponent.
 * <ul>
 * <li>Copyright 2017 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Thu May 18 12:31:19 COT 2017
 * @since SIAA-CORE 2.1
 */
 
@Component({
  selector: 'app-justificarasistencia',
  templateUrl: './justificarasistencia.component.html'
})
export class JustificarAsistenciaAlumnoComponent extends BaseComponent implements  OnInit, OnChanges,AfterViewInit {
    
  

	constructor(private fb: FormBuilder,public dialog: MatDialog, public snackbar: MatSnackBar, router : Router,route:  ActivatedRoute) { 
		super(dialog,snackbar,router,route);
		//this.debounceTimeProcesar().subscribe(term =>  {this.search = term; this.buscar()});
	}
	
	ngAfterViewInit() {
    // viewChild is set after the view has been initialized
	}

    ngOnChanges(changes: SimpleChanges) { 
        this.frmGroup = this.fb.group({
            justificacion: ['']
            //listaGenPostulante:
        });
        this.frmGroup.get('justificacion').setValue(this.data.justificacion);
			this.showAccion();
    }
	
	ngOnInit() {
		this.onInit();
	
	}
	 
	onInit() {  

  }
  
  

	public listo() {
	    this.guardarJustificacion();
	}



public  guardarJustificacion() : void {
	this.dialogRef.close(this.frmGroup.value.justificacion);
	this.showModal = false;
}	

	/**
	getBufferedData.
	*
	*/
	public getBufferedData(event : any) {
		this.dataProvider = event.dataProvider;
	}
	
}