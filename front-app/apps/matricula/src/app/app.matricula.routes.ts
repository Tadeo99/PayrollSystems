import { Route } from '@angular/router';

export const appRoutes: Route[] = [
  {
    path: '',
    loadChildren: () =>
      import('./remote-entry/entry.module').then((m) => m.RemoteEntryModule),
  },
  {
    path: 'grupo',
    loadChildren: () =>
      import('./grupo/grupo.module').then((m) => m.GrupoModule),
  },
  {
    path: 'pabellon',
    loadChildren: () =>
      import('./pabellon/pabellon.module').then((m) => m.PabellonModule),
  },
];
