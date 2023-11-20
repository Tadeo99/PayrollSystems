import { NgModule,ModuleWithProviders } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { SharedModule }  from '../../../shared/shared.module';
import { AdministrarSistemaComponent,DialogContentOverride } from './administrarsistema.component';
import { GrupoUsuarioUsuarioComponent } from '../grupousuariousuario/grupousuariousuario.component';
import { UsuarioSelectComponent } from '../usuarioselect/usuarioselect.component';
import { MenuPersonalizadoComponent } from '../menupersonalizado/menupersonalizado.component';
import { GrupoUsuarioMenuComponent } from '../grupousuariomenu/grupousuariomenu.component';
import { UsuarioEntidadComponent } from '../usuarioentidad/usuarioentidad.component';
import { PrivilegioGrupoUsuarioComponent } from '../privilegiogrupousuario/privilegiogrupousuario.component';
import { PrivilegioPersonalizadoComponent } from '../privilegiopersonalizado/privilegiopersonalizado.component';
import { PrivilegioMenuComponent } from '../privilegiomenu/privilegiomenu.component';

import { SharedSeguridadModule }  from '../../seguridad/shared.seguridad.module';

/**
 * La Class PropertiesModulo.
 * <ul>
 * <li>Copyright 2017 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Mon Oct 16 14:31:03 COT 2017
 * @since SIAA-CORE 2.1
 */
const routes: Routes = [{ path: '', component: AdministrarSistemaComponent }];
export const routing = RouterModule.forChild(routes);
@NgModule({
  imports: [
   SharedModule,SharedSeguridadModule,routing
  ],
  declarations: [
    AdministrarSistemaComponent,
    GrupoUsuarioUsuarioComponent,
    UsuarioSelectComponent,
    MenuPersonalizadoComponent,
    GrupoUsuarioMenuComponent,
    UsuarioEntidadComponent,
    PrivilegioGrupoUsuarioComponent,
    PrivilegioPersonalizadoComponent,
    PrivilegioMenuComponent,
    DialogContentOverride
  ],
  exports: [
    AdministrarSistemaComponent,
    GrupoUsuarioUsuarioComponent,
    UsuarioSelectComponent,
    MenuPersonalizadoComponent,
    GrupoUsuarioMenuComponent,
    UsuarioEntidadComponent,
    PrivilegioGrupoUsuarioComponent,
    PrivilegioPersonalizadoComponent,
    PrivilegioMenuComponent
  ],
  entryComponents: [DialogContentOverride],
  providers: [
  ]
})
export class AdministrarSistemaModule { }