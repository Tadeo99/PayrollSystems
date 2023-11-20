import { NgModule,ModuleWithProviders } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { SharedModule }  from '../../../shared/shared.module';
import { PropertiesLenguajeComponent } from './propertieslenguaje.component';
/**
 * La Class PropertiesLenguajeModulo.
 * <ul>
 * <li>Copyright 2017 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Mon Oct 16 14:31:03 COT 2017
 * @since SIAA-CORE 2.1
 */
const routes: Routes = [{ path: '', component: PropertiesLenguajeComponent }];
export const routing = RouterModule.forChild(routes);
@NgModule({
  imports: [
   SharedModule,routing
  ],
  declarations: [
    PropertiesLenguajeComponent
  ],
  exports: [
    PropertiesLenguajeComponent
  ],
  providers: [
  ]
})
export class PropertiesLenguajeModule { }