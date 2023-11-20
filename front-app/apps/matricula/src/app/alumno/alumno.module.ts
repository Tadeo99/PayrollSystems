import { NgModule,ModuleWithProviders } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { SharedModule }  from '../../../shared/shared.module';
import { SharedAdmisionModule } from '../../admision/shared.admision.module';
import { AlumnoComponent, DialogContentOverride } from './alumno.component';
/**
 * La Class AlumnoModulo.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Sun May 02 02:32:49 COT 2021
 * @since BUILDERP-CORE 2.1
 */
const routes: Routes = [{ path: '', component: AlumnoComponent }];
export const routing = RouterModule.forChild(routes);
@NgModule({
  imports: [
   SharedModule,SharedAdmisionModule, routing
  ],
  declarations: [
    AlumnoComponent,
    DialogContentOverride
  ],
  exports: [
    AlumnoComponent
  ],
  entryComponents:[DialogContentOverride],
  providers: [
  ]
})
export class AlumnoModule { }