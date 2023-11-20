import { NgModule,ModuleWithProviders } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { SharedModule }  from '../../../shared/shared.module';
import { SharedPagoModule } from '../shared.pago.module';
import { CatalogoCuentaComponent, DialogContentOverride } from './catalogocuenta.component';
/**
 * La Class CatalogoCuentaModulo.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Sun May 02 02:32:52 COT 2021
 * @since BUILDERP-CORE 2.1
 */
const routes: Routes = [{ path: '', component: CatalogoCuentaComponent }];
export const routing = RouterModule.forChild(routes);
@NgModule({
  imports: [
   SharedModule,SharedPagoModule,routing
  ],
  declarations: [
   // CatalogoCuentaComponent
   DialogContentOverride
  ],
  exports: [
    //CatalogoCuentaComponent
  ],
  entryComponents:[DialogContentOverride],
  providers: [
  ]
})
export class CatalogoCuentaModule { }