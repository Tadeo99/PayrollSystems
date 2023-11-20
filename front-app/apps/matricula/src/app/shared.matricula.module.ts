import { NgModule ,ModuleWithProviders } from '@angular/core';

import { SharedModule }  from '../../shared/shared.module';
import { GrupoComponent } from './grupo/grupo.component';
import { PabellonComponent } from './pabellon/pabellon.component';
import { PeriodoComponent } from './periodo/periodo.component';
@NgModule({
  imports: [
    SharedModule
  ],
  declarations: [
    PabellonComponent,
   // PeriodoComponent,
    GrupoComponent,
  ],
  exports: [
    PabellonComponent,
   // PeriodoComponent,
    GrupoComponent,
  ],
  providers: [
 
  ]
})
export class SharedMatriculaModule { 

  static forRoot() {
    return {
      ngModule: SharedMatriculaModule,
      providers: []
    };
  }
}

 