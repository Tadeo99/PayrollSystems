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
import { ProyectoComponent } from './proyecto.component';

import { MatCardModule } from '@angular/material/card';
import { MatListModule } from '@angular/material/list';
import { MatMenuModule } from '@angular/material/menu';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatSelectModule } from '@angular/material/select';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatSortModule } from '@angular/material/sort';
import { MatTabsModule } from '@angular/material/tabs';
import { MatTooltipModule } from '@angular/material/tooltip';
import { BsFindByKeyPipeModule } from '@ng-mf/shared/bs';
import { GrupoNegocioComponent } from './gruponegocio';
import { ModeloComponent } from './modelo';
import { AtributoComponent } from './modelo/atributo';
import { ProyectoFrmComponent } from './proyectofrm';
//import { ProyectoResolver } from './grupousuario.resolvers';
/**
 * La Class ProyectoModulo.
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
    component: ProyectoComponent /*,
    resolve: {
      listaData: ProyectoResolver,
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

    MatMenuModule,
    BsFindByKeyPipeModule,
  ],
  declarations: [
    ProyectoComponent,
    ProyectoFrmComponent,
    GrupoNegocioComponent,
    ModeloComponent,
    AtributoComponent,
  ],
  exports: [
    ProyectoComponent,
    ProyectoFrmComponent,
    GrupoNegocioComponent,
    ModeloComponent,
    AtributoComponent,
  ],
  providers: [],
})
export class ProyectoModule {}
