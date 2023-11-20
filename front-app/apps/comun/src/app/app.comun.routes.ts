import { Route } from '@angular/router';

export const appRoutes: Route[] = [
  {
    path: '',
    loadChildren: () =>
      import('./anhio/anhio.module').then((m) => m.AnhioModule),
  },
  {
    path: 'listaitems',
    loadChildren: () =>
      import('./listaitems/listaitems.module').then((m) => m.ListaItemsModule),
  },
  {
    path: 'parametro',
    loadChildren: () =>
      import('./parametro/parametro.module').then((m) => m.ParametroModule),
  },
  {
    path: 'ubigeo',
    loadChildren: () =>
      import('./ubigeo/ubigeo.module').then((m) => m.UbigeoModule),
  },
  {
    path: 'ecomerce',
    loadChildren: () =>
      import('./ecommerce/ecommerce.module').then((m) => m.ECommerceModule),
  },
];
