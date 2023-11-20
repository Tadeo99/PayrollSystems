module.exports = {
  name: 'aas',
  exposes: {
    './Module': 'apps/aas/src/app/sign-in/sign-in.module.ts',
    './AuthConfirmationRequiredModule':'apps/aas/src/app/confirmation-required/confirmation-required.module.ts',
    './AuthForgotPasswordModule':'apps/aas/src/app/forgot-password/forgot-password.module.ts',
    './AuthResetPasswordModule': 'apps/aas/src/app/reset-password/reset-password.module.ts',
    './AuthSignUpModule': 'apps/aas/src/app/sign-up/sign-up.module.ts',
    './AuthSignOutModule': 'apps/aas/src/app/sign-out/sign-out.module.ts',
    './AuthUnlockSessionModule': 'apps/aas/src/app/unlock-session/unlock-session.module.ts'
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
