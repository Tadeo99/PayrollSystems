import { Route } from '@angular/router';

export const appRoutes: Route[] = [
  {
    path: '',
    loadChildren: () =>
      import('./remote-entry/entry.module').then((m) => m.RemoteEntryModule),
  },
  {
    path: 'tipoUsuario',
    loadChildren: () =>
      import('./tipousuario/tipousuario.module').then((m) => m.TipoUsuarioModule),
  },
  {
    path: 'grupoUsuario',
    loadChildren: () =>
      import('./grupousuario/grupousuario.module').then((m) => m.GrupoUsuarioModule),
  },
  {
    path: 'sistema',
    loadChildren: () =>
      import('./sistema/sistema.module').then((m) => m.SistemaModule),
  },
  {
    path: 'properties',
    loadChildren: () =>
      import('./properties/properties.module').then((m) => m.PropertiesModule),
  },
  {
    path: 'privilegio',
    loadChildren: () =>
      import('./privilegio/privilegio.module').then((m) => m.PrivilegioModule),
  },
  {
    path: 'entidad',
    loadChildren: () =>
      import('./entidad/entidad.module').then((m) => m.EntidadModule),
  },
  {
    path: 'usuario',
    loadChildren: () =>
      import('./usuario/usuario.module').then((m) => m.UsuarioModule),
  },
  {
    path: 'menu',
    loadChildren: () =>
      import('./menu/menu.module').then((m) => m.MenuModule),
  },
];
