import { Route } from '@angular/router';

export const appRoutes: Route[] = [
  {
    path: '',
    loadChildren: () =>
      import('./remote-entry/entry.module').then((m) => m.RemoteEntryModule),
  },
  {
    path: 'tecnologia',
    loadChildren: () =>
      import('./tecnologia/tecnologia.module').then((m) => m.TecnologiaModule),
  },
  {
    path: 'proyecto',
    loadChildren: () =>
      import('./proyecto/proyecto.module').then((m) => m.ProyectoModule),
  },
];
