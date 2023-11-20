import { Route } from '@angular/router';

export const appRoutes: Route[] = [
  {
    path: '',
    loadChildren: () =>
      import('./remote-entry/entry.module').then((m) => m.RemoteEntryModule),
  },
  {
    path: 'tipoPlanilla',
    loadChildren: () =>
      import('./tipoplanilla/tipoplanilla.module').then((m) => m.TipoPlanillaModule),
  },
  {
    path: 'valoresuit',
    loadChildren: () =>
      import('./valoresuit/valoresuit.module').then((m) => m.ValoresUITModule),
  },
  {
    path: 'conceptopdt',
    loadChildren: () =>
      import('./conceptopdt/conceptopdt.module').then((m) => m.ConceptoPdtModule),
  },
  {
    path: 'conceptoregimenpensionario',
    loadChildren: () =>
      import('./conceptoregimenpensionario/conceptoregimenpensionario.module').then((m) => m.ConceptoRegimenPensionarioModule),
  },
  {
    path: 'periodoplanilla',
    loadChildren: () =>
      import('./periodoplanilla/periodoplanilla.module').then((m) => m.PeriodoPlanillaModule),
  },
  {
    path: 'personalPlanilla',
    loadChildren: () =>
      import('./personalplanilla/personalplanilla.module').then((m) => m.PersonalPlanillaModule),
  },
  {
    path: 'personalConcepto',
    loadChildren: () =>
      import('./personalconcepto/personalconcepto.module').then((m) => m.PersonalConceptoModule),
  },
  {
    path: 'planilla',
    loadChildren: () =>
      import('./planilla/planilla.module').then((m) => m.PlanillaModule),
  },
  {
    path: 'tareoPersonal',
    loadChildren: () =>
      import('./tareopersonal/tareopersonal.module').then((m) => m.TareoPersonalModule),
  },
  {
    path: 'epsPersonal',
    loadChildren: () =>
      import('./epspersonal/epspersonal.module').then((m) => m.EPSPersonalModule),
  },
  {
    path: 'vacacionesPersonal',
    loadChildren: () =>
      import('./vacacionespersonal/vacacionespersonal.module').then((m) => m.VacacionesPersonalModule),
  },
  {
    path: 'epsConf',
    loadChildren: () =>
      import('./epsconf/epsconf.module').then((m) => m.EPSConfModule),
  },
  {
    path: 'feriado',
    loadChildren: () =>
      import('./feriado/feriado.module').then((m) => m.FeriadoModule),
  },
];
