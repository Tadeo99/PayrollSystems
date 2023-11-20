import { NgModule } from '@angular/core';
import { MatLuxonDateModule } from '@angular/material-luxon-adapter';
import { MatButtonModule } from '@angular/material/button';
import { MatButtonToggleModule } from '@angular/material/button-toggle';
import { MatCardModule } from '@angular/material/card';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatDividerModule } from '@angular/material/divider';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatListModule } from '@angular/material/list';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatRadioModule } from '@angular/material/radio';
import { MatSelectModule } from '@angular/material/select';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { MatSliderModule } from '@angular/material/slider';
import { MatSortModule } from '@angular/material/sort';
import { MatStepperModule } from '@angular/material/stepper';
import { MatTabsModule } from '@angular/material/tabs';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatTooltipModule } from '@angular/material/tooltip';
import { RouterModule, Routes } from '@angular/router';
import { BsAlertModule, BsCardModule, SharedModule } from '@ng-mf/shared/components/core';
import { ApoderadoFrmComponent } from './apoderadofrm';
import { PostulanteFrmComponent } from './postulantefrm';
import { RegistrarAdmisionComponent } from './registraradmision.component';




/**
 * La Class RegistrarAdmisionModule.
 * <ul>
 * <li>Copyright 2017 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Mon Oct 16 14:31:03 COT 2017
 * @since SIAA-CORE 2.1
 */
const routes: Routes = [{ path: '', component: RegistrarAdmisionComponent }];
export const routing = RouterModule.forChild(routes);
@NgModule({
  imports: [
    SharedModule,
    BsCardModule,
    BsAlertModule,
    MatCardModule,
    MatListModule,
    MatIconModule,
    MatInputModule,
    MatButtonModule,
    MatStepperModule,
    MatSelectModule,
    MatSliderModule,
    MatFormFieldModule,
    MatDividerModule,
    MatSidenavModule,
    MatExpansionModule,
    MatButtonToggleModule,
    MatSlideToggleModule,

    MatTabsModule,
    MatToolbarModule,
    MatTooltipModule,

    MatCheckboxModule,
    MatRadioModule,
    MatProgressBarModule,
    MatSortModule,
    MatDatepickerModule,
    MatLuxonDateModule,
    MatProgressSpinnerModule,

    routing,
  ],
  declarations: [
    RegistrarAdmisionComponent,
    ApoderadoFrmComponent,
    PostulanteFrmComponent
  ],
  exports: [
    RegistrarAdmisionComponent,
    ApoderadoFrmComponent,
    PostulanteFrmComponent
  ],
  providers: [
  ]
})
export class RegistrarAdmisionModule { }