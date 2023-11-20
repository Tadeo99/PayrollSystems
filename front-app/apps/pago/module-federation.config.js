module.exports = {
  name: 'pago',
  exposes: {
    './Module': 'apps/pago/src/app/remote-entry/entry.module.ts',
    './ClasificacionModule': 'apps/pago/src/app/clasificacion/clasificacion.module.ts',
	'./TipoDocSunatEntidadModule': 'apps/pago/src/app/tipodocsunatentidad/tipodocsunatentidad.module.ts',
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
