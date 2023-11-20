import { NgModule,ModuleWithProviders } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { SharedModule }  from '../../../shared/shared.module';
import { PeriodoComponent } from './periodo.component';
import { SharedMatriculaModule }  from '../shared.matricula.module';

/**
 * La Class PeriodoModulo.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Sun May 02 02:32:51 COT 2021
 * @since BUILDERP-CORE 2.1
 */
const routes: Routes = [{ path: '', component: PeriodoComponent }];
export const routing = RouterModule.forChild(routes);
@NgModule({
  imports: [
   SharedModule,
   SharedMatriculaModule,
   routing
  ],
  declarations: [
    //PeriodoComponent
  ],
  exports: [
    //PeriodoComponent
  ],
  providers: [
  ]
})
export class PeriodoModule { }