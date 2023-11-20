import { NgModule,ModuleWithProviders } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { SharedModule }  from '../../../shared/shared.module';
import { DetControlPagoComponent } from '../detcontrolpago/detcontrolpago.component';
import { SharedPagoModule } from '../shared.pago.module';
import { ControlPagoComponent, DialogContentOverride } from './controlpago.component';
/**
 * La Class ControlPagoModulo.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Sun May 02 02:32:52 COT 2021
 * @since BUILDERP-CORE 2.1
 */
const routes: Routes = [{ path: '', component: ControlPagoComponent }];
export const routing = RouterModule.forChild(routes);
@NgModule({
  imports: [
   SharedModule,SharedPagoModule,routing
  ],
  declarations: [
    DialogContentOverride,
    ControlPagoComponent,
    DetControlPagoComponent,
  ],
  exports: [
    ControlPagoComponent,
    DetControlPagoComponent,
  ],
  entryComponents: [DialogContentOverride],
  providers: [
  ]
})
export class ControlPagoModule { }