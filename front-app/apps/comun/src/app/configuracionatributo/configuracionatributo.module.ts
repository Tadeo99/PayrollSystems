import { NgModule,ModuleWithProviders } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { SharedModule }  from '../../../shared/shared.module';
import { ConfiguracionAtributoComponent } from './configuracionatributo.component';
/**
 * La Class ConfiguracionAtributoModulo.
 * <ul>
 * <li>Copyright 2017 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Sat Dec 23 17:16:34 COT 2017
 * @since SIAA-CORE 2.1
 */
const routes: Routes = [{ path: '', component: ConfiguracionAtributoComponent }];
export const routing = RouterModule.forChild(routes);
@NgModule({
  imports: [
   SharedModule,routing
  ],
  declarations: [
    ConfiguracionAtributoComponent
  ],
  exports: [
    ConfiguracionAtributoComponent
  ],
  providers: [
  ]
})
export class ConfiguracionAtributoModule { }