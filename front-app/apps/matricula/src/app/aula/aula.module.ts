import { NgModule,ModuleWithProviders } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { SharedModule }  from '../../../shared/shared.module';
import { AulaComponent,DialogContentOverride } from './aula.component';
import { SharedMatriculaModule }  from '../shared.matricula.module';

/**
 * La Class AulaModulo.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Sun May 02 02:32:49 COT 2021
 * @since BUILDERP-CORE 2.1
 */
const routes: Routes = [{ path: '', component: AulaComponent }];
export const routing = RouterModule.forChild(routes);
@NgModule({
  imports: [
   SharedModule,
   SharedMatriculaModule,
   routing
  ],
  declarations: [
   // AulaComponent,
    DialogContentOverride
  ],
  exports: [
    //AulaComponent
  ],
  entryComponents: [DialogContentOverride],
  providers: [
  ]
})
export class AulaModule { }