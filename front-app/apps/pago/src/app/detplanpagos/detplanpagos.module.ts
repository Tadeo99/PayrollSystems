import { NgModule,ModuleWithProviders } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { SharedModule }  from '../../../shared/shared.module';
import { DetPlanPagosComponent } from './detplanpagos.component';
/**
 * La Class DetPlanPagosModulo.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Sun May 02 02:32:53 COT 2021
 * @since BUILDERP-CORE 2.1
 */
const routes: Routes = [{ path: '', component: DetPlanPagosComponent }];
export const routing = RouterModule.forChild(routes);
@NgModule({
  imports: [
   SharedModule,routing
  ],
  declarations: [
  // DetPlanPagosComponent
  ],
  exports: [
   // DetPlanPagosComponent
  ],
  providers: [
  ]
})
export class DetPlanPagosModule { }