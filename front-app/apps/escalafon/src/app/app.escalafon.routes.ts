import { Route } from '@angular/router';

export const appRoutes: Route[] = [
  {
    path: '',
    loadChildren: () =>
      import('./remote-entry/entry.module').then((m) => m.RemoteEntryModule),
  },
  {
    path: 'institucion',
    loadChildren: () =>
      import('./institucion/institucion.module').then((m) => m.InstitucionModule),
  },
  {
    path: 'carrera',
    loadChildren: () =>
      import('./carrera/carrera.module').then((m) => m.CarreraModule),
  },
  {
    path: 'centroCosto',
    loadChildren: () =>
      import('./centrocosto/centrocosto.module').then((m) => m.CentroCostoModule),
  },
  {
    path: 'personal',
    loadChildren: () =>
      import('./personal/personal.module').then((m) => m.PersonalModule),
  },
];
