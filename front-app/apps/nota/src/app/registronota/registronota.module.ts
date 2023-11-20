import { NgModule,ModuleWithProviders } from '@angular/core';
import { Routes, RouterModule } from '@angular/router'; 
import { SharedModule }  from '../../../shared/shared.module';
import { CriterioEvaluacionModalComponent } from '../../matricula/criterioevaluacion/criterioevaluacionmodal.component';
import { DetRegistroNotaComponent, DialogContentOverride } from '../detregistronota';
import { CriterioEvaluacionRecursiveComponent } from '../detregistronota/criterioevaluacionrecursive.component';
import { DetRegistroNotaModule } from '../detregistronota/detregistronota.module';
import { RegistroNotaComponent } from './registronota.component'; 
/**
 * La Class RegistroNotaModulo.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Thu Jun 10 00:13:46 COT 2021
 * @since BUILDERP-CORE 2.1
 */
const routes: Routes = [{ path: '', component: RegistroNotaComponent }];
export const routing = RouterModule.forChild(routes);
@NgModule({
  imports: [
    SharedModule,routing
  ],
  declarations: [
    RegistroNotaComponent,
    DetRegistroNotaComponent ,
    CriterioEvaluacionRecursiveComponent,
    CriterioEvaluacionModalComponent,
    DialogContentOverride 
    
  ],
  exports: [
    RegistroNotaComponent,
    DetRegistroNotaComponent  ,
    CriterioEvaluacionRecursiveComponent,
    CriterioEvaluacionModalComponent
  ],
  providers: [
  ]
})
export class RegistroNotaModule { }