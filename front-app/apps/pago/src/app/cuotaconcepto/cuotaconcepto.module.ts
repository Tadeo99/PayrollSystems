import { NgModule,ModuleWithProviders } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { SharedModule }  from '../../../shared/shared.module';
import { SharedPagoModule } from '../shared.pago.module';
import { CuotaConceptoComponent, DialogContentOverride } from './cuotaconcepto.component';
/**
 * La Class CuotaConceptoModulo.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Sun May 02 02:32:52 COT 2021
 * @since BUILDERP-CORE 2.1
 */
const routes: Routes = [{ path: '', component: CuotaConceptoComponent }];
export const routing = RouterModule.forChild(routes);
@NgModule({
  imports: [
   SharedModule,SharedPagoModule,routing
  ],
  declarations: [
    //CuotaConceptoComponent,
    DialogContentOverride,
  ],
  exports: [
    //CuotaConceptoComponent
  ],
  entryComponents: [DialogContentOverride],
  providers: [
  ]
})
export class CuotaConceptoModule { }