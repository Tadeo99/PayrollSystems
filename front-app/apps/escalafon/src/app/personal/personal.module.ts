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
import { AsociarCentroCostoComponent } from './asociarcentrocosto';
import { BeneficiariosComponent } from './beneficiarios';
import { ContratoComponent } from './contrato';
import { CuentaBancariaPersonalComponent } from './cuentabancariapersonal/cuentabancariapersonal.component';
import { DireccionPersonalComponent } from './direccionpersonal/direccionpersonal.component';
import { HistorialBasicoComponent } from './historialbasico';
import { HistorialCargoAreaServiceComponent } from './historialcargoarea';
import { PeriodoLaboraPersonalComponent } from './periodolaborapersonal';
import { PersonalComponent } from './personal.component';
import { PersonalFrmComponent } from './personalfrm';


//import { PersonalResolver } from './personal.resolvers';
/**
 * La Class PersonalModulo.
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
    path: '', component: PersonalComponent/*,
    resolve: {
      listaData: PersonalResolver,
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
    MatTabsModule
  ],
  declarations: [
    PersonalFrmComponent,
    DireccionPersonalComponent,
    CuentaBancariaPersonalComponent,
    ContratoComponent,
    PeriodoLaboraPersonalComponent,
    AsociarCentroCostoComponent,
    BeneficiariosComponent,
    HistorialBasicoComponent,
    HistorialCargoAreaServiceComponent,
    PersonalComponent

  ],
  exports: [
    PersonalFrmComponent,
    DireccionPersonalComponent,
    CuentaBancariaPersonalComponent,
    ContratoComponent,
    PeriodoLaboraPersonalComponent,
    AsociarCentroCostoComponent,
    BeneficiariosComponent,
    HistorialBasicoComponent,
    HistorialCargoAreaServiceComponent,
    PersonalComponent
  ],
  providers: [
  ]
})
export class PersonalModule { }