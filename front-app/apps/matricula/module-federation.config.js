module.exports = {
  name: 'matricula',
  exposes: {
    './Module': 'apps/matricula/src/app/remote-entry/entry.module.ts',
    './GrupoModule': 'apps/matricula/src/app/grupo/grupo.module.ts',
    './PabellonModule': 'apps/matricula/src/app/pabellon/pabellon.module.ts',
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
