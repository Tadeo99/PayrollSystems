import { Route } from '@angular/router';

export const appRoutes: Route[] = [
  {
    path: '',
    loadChildren: () =>
      import('./remote-entry/entry.module').then((m) => m.RemoteEntryModule),
  },
  {
    path: 'clasificacion',
    loadChildren: () =>
      import('./clasificacion/clasificacion.module').then((m) => m.ClasificacionModule),
  },
  {
    path: 'tipodocsunatentidad',
    loadChildren: () =>
      import('./tipodocsunatentidad/tipodocsunatentidad.module').then((m) => m.TipoDocSunatEntidadModule),
  },
];
