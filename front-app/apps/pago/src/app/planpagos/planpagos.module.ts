import { NgModule,ModuleWithProviders } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { SharedModule }  from '../../../shared/shared.module';
import { DetPlanPagosComponent } from '../detplanpagos/detplanpagos.component';
import { SharedPagoModule } from '../shared.pago.module';
import {  DialogContentOverride, PlanPagosComponent } from './planpagos.component';
/**
 * La Class PlanPagosModulo.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Sun May 02 02:32:53 COT 2021
 * @since BUILDERP-CORE 2.1
 */
const routes: Routes = [{ path: '', component: PlanPagosComponent }];
export const routing = RouterModule.forChild(routes);
@NgModule({
  imports: [
   SharedModule,SharedPagoModule,routing
  ],
  declarations: [
    PlanPagosComponent,
    DetPlanPagosComponent,
    DialogContentOverride,
  ],
  exports: [
    PlanPagosComponent,
    DetPlanPagosComponent
  ],
  entryComponents: [DialogContentOverride],
  providers: [
  ]
})
export class PlanPagosModule { }