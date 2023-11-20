import { NgModule } from '@angular/core';
import { MatLuxonDateModule } from '@angular/material-luxon-adapter';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatListModule } from '@angular/material/list';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSelectModule } from '@angular/material/select';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatSortModule } from '@angular/material/sort';
import { MatTabsModule } from '@angular/material/tabs';
import { MatTooltipModule } from '@angular/material/tooltip';
import { RouterModule, Routes } from '@angular/router';
import { BsAlertModule, BsCardModule, SharedModule } from '@ng-mf/shared/components/core';
import { SharedConceptoPdtModule } from '../conceptopdtselect/shared.conceptopdt.module';
import { AdelantoComponent } from './adelanto';
import { ConceptoFijoTrabajadorComponent } from './conceptofijotrabajador';
import { InformaOtrosIngreso5taServiceComponent } from './informaotrosingreso5ta';
import { PersonalPlanillaComponent } from './personalplanilla.component';
import { VacacionesComponent } from './vacaciones';


//import { PersonalPlanillaResolver } from './personalPlanilla.resolvers';
/**
 * La Class PersonalPlanillaModulo.
 * <ul>
 * <li>Copyright 2020 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Sun May 02 02:32:49 COT 2021
 * @since BUILDERP-CORE 2.1
 */
const routes: Routes = [
  {
    path: '', component: PersonalPlanillaComponent/*,
    resolve: {
      listaData: PersonaPlanillalResolver,
    }*/
  }
];
export const routing = RouterModule.forChild(routes);
@NgModule({
  imports: [
    routing,
    MatButtonModule,
    MatCheckboxModule,
    MatFormFieldModule,
    MatIconModule,
    MatInputModule,
    MatProgressSpinnerModule,
    BsCardModule,
    BsAlertModule,
    SharedModule,

    MatCardModule,
    MatListModule,
    MatSelectModule,
    MatPaginatorModule,
    MatTooltipModule,
    MatProgressBarModule,
    MatSortModule,
    MatDatepickerModule,
    MatLuxonDateModule,
    MatSidenavModule,
    MatTabsModule,
    SharedConceptoPdtModule
  ],
  declarations: [
    ConceptoFijoTrabajadorComponent,
    AdelantoComponent,
    VacacionesComponent,
    PersonalPlanillaComponent,
    InformaOtrosIngreso5taServiceComponent,

  ],
  exports: [
    ConceptoFijoTrabajadorComponent,
    AdelantoComponent,
    VacacionesComponent,
    PersonalPlanillaComponent,
    InformaOtrosIngreso5taServiceComponent,
  ],
  providers: [
  ]
})
export class PersonalPlanillaModule { }