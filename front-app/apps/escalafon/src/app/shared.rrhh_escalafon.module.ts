import { NgModule ,ModuleWithProviders } from '@angular/core';

import { SharedModule }  from '../../shared/shared.module';
import { CarreraComponent } from './carrera';
import { InstitucionComponent } from './institucion';
import { PeriodoLaboraPersonalComponent } from './periodolaborapersonal/periodolaborapersonal.component';
@NgModule({
  imports: [
    SharedModule
  ],
  declarations: [
    PeriodoLaboraPersonalComponent,
    CarreraComponent,
    InstitucionComponent,
  ],
  exports: [
    PeriodoLaboraPersonalComponent,
    CarreraComponent,
    InstitucionComponent,
  ],
  providers: [
 
  ]
})
export class SharedRRHH_EscalafonModule { 

  static forRoot() {
    return {
      ngModule: SharedRRHH_EscalafonModule,
      providers: []
    };
  }
}

 