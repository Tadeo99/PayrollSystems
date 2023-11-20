module.exports = {
  name: 'comun',
  exposes: {
    './AnhioModule': 'apps/comun/src/app/anhio/anhio.module.ts',
    './ListaItemsModule': 'apps/comun/src/app/listaitems/listaitems.module.ts',
    './ParametroModule': 'apps/comun/src/app/parametro/parametro.module.ts',
    './UbigeoModule': 'apps/comun/src/app/ubigeo/ubigeo.module.ts',
    './ECommerceModule': 'apps/comun/src/app/ecommerce/ecommerce.module.ts',
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
