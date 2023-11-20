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
import { MatSortModule } from '@angular/material/sort';
import { MatTooltipModule } from '@angular/material/tooltip';
import { RouterModule, Routes } from '@angular/router';
import { BsAlertModule, BsCardModule, SharedModule } from '@ng-mf/shared/components/core';
import { CentroCostoComponent } from './centrocosto.component';


//import { MatTabsModule } from '@angular/material/tabs';
//import { CentroCostoResolver } from './centrocosto.resolvers';
/**
 * La Class CentroCostoModulo.
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
    path: '', component: CentroCostoComponent/*,
    resolve: {
      listaData: CentroCostoResolver,
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
    MatLuxonDateModule
  ],
  declarations: [
    CentroCostoComponent
  ],
  exports: [
    CentroCostoComponent
  ],
  providers: [

  ]
})
export class CentroCostoModule { }