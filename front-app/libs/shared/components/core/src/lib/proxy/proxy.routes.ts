import { Route } from '@angular/router';

export const routes: Route[] = [
    {
        path: '',
        loadComponent: async () => (await import('./proxy.component')).ProxyComponent
    }
];