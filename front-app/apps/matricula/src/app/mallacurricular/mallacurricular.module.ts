import { NgModule,ModuleWithProviders } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { SharedModule }  from '../../../shared/shared.module';
import { CapacidadComponent, DialogContentOverrideCapacidad } from '../capacidad/capacidad.component';
import { CapacidadModalEditComponent } from '../capacidad/capacidadmodaledit.component';
import { CompetenciaComponent, DialogContentOverride } from '../competencia/competencia.component';
import { CriterioEvaluacionComponent } from '../criterioevaluacion/criterioevaluacion.component';
import { DesempenhoComponent, DialogContentOverrideDesempenho } from '../desempenho/desempenho.component';
import { DesempenhoModalEditComponent } from '../desempenho/desempenhomodaledit.component';
import { DetMallaCurricularComponent } from '../detmallacurricular/detmallacurricular.component';
import { MallaCurricularComponent } from './mallacurricular.component';

/**
 * La Class MallaCurricularModulo.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Sun May 02 02:32:50 COT 2021
 * @since BUILDERP-CORE 2.1
 */
const routes: Routes = [{ path: '', component: MallaCurricularComponent }];
export const routing = RouterModule.forChild(routes);
@NgModule({
  imports: [
   SharedModule,routing
  ],
  declarations: [
    MallaCurricularComponent,
    DetMallaCurricularComponent,
    CompetenciaComponent,
    CapacidadComponent,
    CapacidadModalEditComponent,
    DialogContentOverride,
    DialogContentOverrideCapacidad,
    DesempenhoComponent,
    DesempenhoModalEditComponent,
    DialogContentOverrideDesempenho,
    CriterioEvaluacionComponent,
  ],
  exports: [
    MallaCurricularComponent,
    DetMallaCurricularComponent,
    CompetenciaComponent,
    CapacidadComponent,
    CapacidadModalEditComponent,
    DesempenhoComponent,
    DesempenhoModalEditComponent,
    CriterioEvaluacionComponent,
  ],
  entryComponents: [DialogContentOverride,DialogContentOverrideCapacidad,DialogContentOverrideDesempenho],
  providers: [
  ]
})
export class MallaCurricularModule { }