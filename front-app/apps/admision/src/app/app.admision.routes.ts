import { Route } from '@angular/router';

export const appRoutes: Route[] = [
  {
    path: '',
    loadChildren: () =>
      import('./remote-entry/entry.module').then((m) => m.RemoteEntryModule),
  },
  {
    path: 'grado',
    loadChildren: () =>
      import('./grado/grado.module').then((m) => m.GradoModule),
  },
  {
    path: 'seccion',
    loadChildren: () =>
      import('./seccion/seccion.module').then((m) => m.SeccionModule),
  },
  {
    path: 'sede',
    loadChildren: () =>
      import('./sede/sede.module').then((m) => m.SedeModule),
  },
  {
    path: 'postulante',
    loadChildren: () =>
      import('./postulante/postulante.module').then((m) => m.PostulanteModule),
  },
  {
    path: 'apoderado',
    loadChildren: () =>
      import('./apoderado/apoderado.module').then((m) => m.ApoderadoModule),
  },
  {
    path: 'notes',
    loadChildren: () =>
      import('./notes/notes.module').then((m) => m.NotesModule),
  },
  {
    path: 'registraradmision',
    loadChildren: () =>
      import('./registraradmision/registraradmision.module').then((m) => m.RegistrarAdmisionModule),
  },
];
