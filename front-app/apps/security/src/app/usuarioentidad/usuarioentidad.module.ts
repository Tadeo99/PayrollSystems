import { NgModule,ModuleWithProviders } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { SharedModule }  from '../../../shared/shared.module';
import { UsuarioEntidadComponent } from './usuarioentidad.component';
/**
 * La Class UsuarioEntidadModulo.
 * <ul>
 * <li>Copyright 2017 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Sat Dec 23 17:16:38 COT 2017
 * @since SIAA-CORE 2.1
 */
const routes: Routes = [{ path: '', component: UsuarioEntidadComponent }];
export const routing = RouterModule.forChild(routes);
@NgModule({
  imports: [
   SharedModule,routing
  ],
  declarations: [
    //UsuarioEntidadComponent
  ],
  exports: [
    //UsuarioEntidadComponent
  ],
  providers: [
  ]
})
export class UsuarioEntidadModule { }