module.exports = {
  name: 'builderp',
  remotes: [],
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
