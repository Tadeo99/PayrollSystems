module.exports = {
  name: 'planilla',
  exposes: {
    './Module': 'apps/planilla/src/app/remote-entry/entry.module.ts',
    './TipoPlanillaModule':
      'apps/planilla/src/app/tipoplanilla/tipoplanilla.module.ts',
    './ValoresUITModule':
      'apps/planilla/src/app/valoresuit/valoresuit.module.ts',
    './ConceptoPdtModule':
      'apps/planilla/src/app/conceptopdt/conceptopdt.module.ts',
    './ConceptoRegimenPensionarioModule':
      'apps/planilla/src/app/conceptoregimenpensionario/conceptoregimenpensionario.module.ts',
    './PeriodoPlanillaModule':
      'apps/planilla/src/app/periodoplanilla/periodoplanilla.module.ts',
    './PersonalPlanillaModule':
      'apps/planilla/src/app/personalplanilla/personalplanilla.module.ts',
    './PersonalConceptoModule':
      'apps/planilla/src/app/personalconcepto/personalconcepto.module.ts',
    './PlanillaModule': 'apps/planilla/src/app/planilla/planilla.module.ts',
    './TareoPersonalModule':
      'apps/planilla/src/app/tareopersonal/tareopersonal.module.ts',
    './EPSPersonalModule':
      'apps/planilla/src/app/epspersonal/epspersonal.module.ts',
    './VacacionesPersonalModule':
      'apps/planilla/src/app/vacacionespersonal/vacacionespersonal.module.ts',
    './EPSConfModule': 'apps/planilla/src/app/epsconf/epsconf.module.ts',
    './FeriadoModule': 'apps/planilla/src/app/feriado/feriado.module.ts'
  },
  // adds vue as shared module
  // version is inferred from package.json
  // it will always use the shared version, but print a warning when the shared vue is < 2.6.5 or >= 3
  additionalShared: [
    {
      libraryName: 'highlight.js',
      sharedConfig: {
        requiredVersion: '^11.7.0',
        singleton: true,
      },
    },
    {
      libraryName: 'luxon',
      sharedConfig: {
        requiredVersion: '^3.2.1',
        singleton: true,
      },
    },
    {
      libraryName: 'lodash',
      sharedConfig: {
        requiredVersion: '^4.17.21',
        singleton: true,
      },
    },
  ],
};
