import { NgModule,ModuleWithProviders } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { SharedModule }  from '../../../shared/shared.module';
import { CriterioEvaluacionComponent } from './criterioevaluacion.component';
/**
 * La Class CriterioEvaluacionModulo.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Thu Jun 10 00:13:45 COT 2021
 * @since BUILDERP-CORE 2.1
 */
const routes: Routes = [{ path: '', component: CriterioEvaluacionComponent }];
export const routing = RouterModule.forChild(routes);
@NgModule({
  imports: [
   SharedModule,routing
  ],
  declarations: [
  //  CriterioEvaluacionComponent
  ],
  exports: [  
  //  CriterioEvaluacionComponent
  ],
  providers: [
  ]
})
export class CriterioEvaluacionModule { }