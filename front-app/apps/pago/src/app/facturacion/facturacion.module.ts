import { SharedModule }  from '../../../shared/shared.module'; 
import { FacturacionComponent } from './facturacion.component';
import { Routes, RouterModule } from '@angular/router';
import { ModuleWithProviders, NgModule } from '@angular/core';
 

/**
 * La Class VentaModulo.
 * <ul>
 * <li>Copyright 2017 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Sat Dec 23 17:16:36 COT 2017
 * @since SIAA-CORE 2.1
 */
const routes: Routes = [{ path: '', component: FacturacionComponent }];
export const routing = RouterModule.forChild(routes);
@NgModule({
  imports: [
   SharedModule, 
   routing
  ],
  declarations: [
    FacturacionComponent
  ],
  exports: [
    FacturacionComponent
  ],
  entryComponents: [],
  providers: [
  ]
})
export class FacturacionModule { }