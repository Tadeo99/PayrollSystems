import { Route } from '@angular/router';

export const appRoutes: Route[] = [
  {
    path: '',
    loadChildren: () =>
      import('./sign-in/sign-in.module').then(
        (m) => m.AuthSignInModule
      ),
  },
  {
    path: 'confirmation-required',
    loadChildren: () =>
      import('./confirmation-required/confirmation-required.module').then(
        (m) => m.AuthConfirmationRequiredModule
      ),
  },
  {
    path: 'forgot-password',
    loadChildren: () =>
      import('./forgot-password/forgot-password.module').then(
        (m) => m.AuthForgotPasswordModule
      ),
  },
  {
    path: 'reset-password',
    loadChildren: () =>
      import('./reset-password/reset-password.module').then(
        (m) => m.AuthResetPasswordModule
      ),
  },
  {
    path: 'sign-out',
    loadChildren: () =>
      import('./sign-out/sign-out.module').then(
        (m) => m.AuthSignOutModule
      ),
  },
  {
    path: 'sign-up',
    loadChildren: () =>
      import('./sign-up/sign-up.module').then(
        (m) => m.AuthSignUpModule
      ),
  },
  {
    path: 'unlock-session',
    loadChildren: () =>
      import('./unlock-session/unlock-session.module').then(
        (m) => m.AuthUnlockSessionModule
      ),
  }
];
