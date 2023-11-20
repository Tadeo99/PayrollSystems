import { NgModule,ModuleWithProviders } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { SharedModule }  from '../../../shared/shared.module';
import { AsignaPostulanteComponent } from './asignapostulante.component';
/**
 * La Class AsignaPostulanteModulo.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Thu Nov 05 22:14:39 COT 2020
 * @since BUILDERP-CORE 2.1
 */
const routes: Routes = [{ path: '', component: AsignaPostulanteComponent }];
export const routing = RouterModule.forChild(routes);
@NgModule({
  imports: [
   SharedModule,routing
  ],
  declarations: [
    AsignaPostulanteComponent
  ],
  exports: [
    AsignaPostulanteComponent
  ],
  providers: [
  ]
})
export class AsignaPostulanteModule { }