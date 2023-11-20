import { NgModule } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { RouterModule, Routes } from '@angular/router';
import {
  BsAlertModule,
  BsCardModule,
  SharedModule,
} from '@ng-mf/shared/components/core';
import { TecnologiaComponent } from './tecnologia.component';

import { MatCardModule } from '@angular/material/card';
import { MatListModule } from '@angular/material/list';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatSelectModule } from '@angular/material/select';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatSortModule } from '@angular/material/sort';
import { MatTabsModule } from '@angular/material/tabs';
import { MatTooltipModule } from '@angular/material/tooltip';
import { EquivalenciaTypeComponent } from './equivalenciatype';
import { PlantillaComponent } from './plantilla';
import { TecnologiaFrmComponent } from './tecnologiafrm';
//import { TecnologiaResolver } from './grupousuario.resolvers';
/**
 * La Class TecnologiaModulo.
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
    path: '',
    component: TecnologiaComponent /*,
    resolve: {
      listaData: TecnologiaResolver,
    }*/,
  },
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
    MatSidenavModule,
    MatTabsModule,
  ],
  declarations: [
    TecnologiaComponent,
    TecnologiaFrmComponent,
    PlantillaComponent,
    EquivalenciaTypeComponent,
  ],
  exports: [
    TecnologiaComponent,
    TecnologiaFrmComponent,
    PlantillaComponent,
    EquivalenciaTypeComponent,
  ],
  providers: [],
})
export class TecnologiaModule {}
