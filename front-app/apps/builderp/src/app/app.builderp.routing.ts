import { Route } from '@angular/router';
import { loadRemoteModule } from '@nrwl/angular/mf';
import { AuthGuard, NoAuthGuard } from '@ng-mf/shared/service/aas';
import { LayoutComponent } from './layout/layout.component';
import { InitialDataResolver } from './app.resolvers';

// @formatter:off
/* eslint-disable max-len */
/* eslint-disable @typescript-eslint/explicit-function-return-type */
export const appRoutes: Route[] = [

    // Redirect empty path to '/dashboards/project'
    { path: '', pathMatch: 'full', redirectTo: 'dashboards/project' },

    // Redirect signed-in user to the '/dashboards/project'
    //
    // After the user signs in, the sign-in page will redirect the user to the 'signed-in-redirect'
    // path. Below is another redirection for that path to redirect the user to the desired
    // location. This is a small convenience to keep all main routes together here on this file.
    { path: 'signed-in-redirect', pathMatch: 'full', redirectTo: 'dashboards/project' },
    { path: 'admision-in-redirect', pathMatch: 'full', redirectTo: 'admision/registrar' },

    // Auth routes for guests
    {
        path: '',
        canMatch: [NoAuthGuard],
        component: LayoutComponent,
        data: {
            layout: 'empty'
        },
        children: [
            { path: 'confirmation-required', loadChildren: () => loadRemoteModule('aas', './AuthConfirmationRequiredModule').then(m => m.AuthConfirmationRequiredModule) },
            { path: 'forgot-password', loadChildren: () => loadRemoteModule('aas', './AuthForgotPasswordModule').then(m => m.AuthForgotPasswordModule) },
            { path: 'reset-password', loadChildren: () => loadRemoteModule('aas', './AuthResetPasswordModule').then(m => m.AuthResetPasswordModule) },
            //{ path: 'sign-in', loadChildren: () => import('./modules/auth/sign-in/sign-in.module').then(m => m.AuthSignInModule) },
            { path: 'sign-in', loadChildren: () => loadRemoteModule('aas', './Module').then((m) => m.AuthSignInModule) },
            { path: 'sign-up', loadChildren: () => loadRemoteModule('aas', './AuthSignUpModule ').then(m => m.AuthSignUpModule) },
            { path: 'admision/registrar', loadChildren: () => loadRemoteModule('admision', './RegistrarAdmisionModule').then(m => m.RegistrarAdmisionModule), data: { title: 'registraradmision.pnl.title' } },
        ]
    },

    // Auth routes for authenticated users
    {
        path: '',
        canMatch: [AuthGuard],
        component: LayoutComponent,
        data: {
            layout: 'empty'
        },
        children: [
            { path: 'sign-out', loadChildren: () => loadRemoteModule('aas', './AuthSignOutModule').then(m => m.AuthSignOutModule) },
            { path: 'unlock-session', loadChildren: () => loadRemoteModule('aas', './AuthUnlockSessionModule').then(m => m.AuthUnlockSessionModule) }
        ]
    },

    // Landing routes
    {
        path: '',
        component: LayoutComponent,
        data: {
            layout: 'empty'
        },
        children: [
            { path: 'home', loadChildren: () => import('./modules/landing/home/home.module').then(m => m.LandingHomeModule) },
        ]
    },

    // Admin routes
    {
        path: '',
        canMatch: [AuthGuard],
        component: LayoutComponent,
        resolve: {
            initialData: InitialDataResolver,
        },
        children: [

            // Dashboards
            {
                path: 'dashboards', children: [
                    { path: 'project', loadChildren: () => import('./modules/admin/dashboards/project/project.module').then(m => m.ProjectModule) },
                    { path: 'analytics', loadChildren: () => import('./modules/admin/dashboards/analytics/analytics.module').then(m => m.AnalyticsModule) },
                    { path: 'finance', loadChildren: () => import('./modules/admin/dashboards/finance/finance.module').then(m => m.FinanceModule) },
                    { path: 'crypto', loadChildren: () => import('./modules/admin/dashboards/crypto/crypto.module').then(m => m.CryptoModule) },
                ]
            },

            // Commun
            {
                path: 'common', children: [
                    { path: 'anhio', loadChildren: () => loadRemoteModule('comun', './AnhioModule').then(m => m.AnhioModule), data: { title: 'anhio.pnl.title' } },
                    { path: 'ubigeo', loadChildren: () => loadRemoteModule('comun', './UbigeoModule').then(m => m.UbigeoModule), data: { title: 'ubigeo.pnl.title' } },
                    { path: 'listaItems', loadChildren: () => loadRemoteModule('comun', './ListaItemsModule').then(m => m.ListaItemsModule), data: { title: 'listaItems.pnl.title' } },
                    { path: 'parametro', loadChildren: () => loadRemoteModule('comun', './ParametroModule').then(m => m.ParametroModule), data: { title: 'parametro.pnl.title' } }
                ]
            },
            // security
            {
                path: 'security', children: [
                    { path: 'tipoUsuario', loadChildren: () => loadRemoteModule('security', './TipoUsuarioModule').then(m => m.TipoUsuarioModule), data: { title: 'tipoUsuario.pnl.title' } },
                    { path: 'grupoUsuario', loadChildren: () => loadRemoteModule('security', './GrupoUsuarioModule').then(m => m.GrupoUsuarioModule), data: { title: 'grupoUsuario.pnl.title' } },
                    { path: 'sistema', loadChildren: () => loadRemoteModule('security', './SistemaModule').then(m => m.SistemaModule), data: { title: 'sistema.pnl.title' } },
                    { path: 'properties', loadChildren: () => loadRemoteModule('security', './PropertiesModule').then(m => m.PropertiesModule), data: { title: 'properties.pnl.title' } },
                    { path: 'privilegio', loadChildren: () => loadRemoteModule('security', './PrivilegioModule').then(m => m.PrivilegioModule), data: { title: 'privilegio.pnl.title' } },
                    { path: 'entidad', loadChildren: () => loadRemoteModule('security', './EntidadModule').then(m => m.EntidadModule), data: { title: 'entidad.pnl.title' } },
                    { path: 'usuario', loadChildren: () => loadRemoteModule('security', './UsuarioModule').then(m => m.UsuarioModule), data: { title: 'usuario.pnl.title' } },
                    { path: 'menu', loadChildren: () => loadRemoteModule('security', './MenuModule').then(m => m.MenuModule), data: { title: 'menu.pnl.title' } }
                ]
            },
            // admision
            {
                path: 'admision', children: [
                    { path: 'grado', loadChildren: () => loadRemoteModule('admision', './GradoModule').then(m => m.GradoModule), data: { title: 'grado.pnl.title' } },
                    { path: 'seccion', loadChildren: () => loadRemoteModule('admision', './SeccionModule').then(m => m.SeccionModule), data: { title: 'seccion.pnl.title' } },
                    { path: 'sede', loadChildren: () => loadRemoteModule('admision', './SedeModule').then(m => m.SedeModule), data: { title: 'sede.pnl.title' } },
                    { path: 'postulante', loadChildren: () => loadRemoteModule('admision', './PostulanteModule').then(m => m.PostulanteModule), data: { title: 'postulante.pnl.title' } },
                    { path: 'apoderado', loadChildren: () => loadRemoteModule('admision', './ApoderadoModule').then(m => m.ApoderadoModule), data: { title: 'apoderado.pnl.title' } },
                    { path: 'registrar', loadChildren: () => loadRemoteModule('admision', './RegistrarAdmisionModule').then(m => m.RegistrarAdmisionModule), data: { title: 'registraradmision.pnl.title' } },
                ]
            },

            // rrhhescalafon
            {
                path: 'rrhhescalafon', children: [
                    { path: 'institucion', loadChildren: () => loadRemoteModule('escalafon', './InstitucionModule').then(m => m.InstitucionModule), data: { title: 'institucion.pnl.title' } },
                    { path: 'carrera', loadChildren: () => loadRemoteModule('escalafon', './CarreraModule').then(m => m.CarreraModule), data: { title: 'carrera.pnl.title' } },
                    { path: 'centroCosto', loadChildren: () => loadRemoteModule('escalafon', './CentroCostoModule').then(m => m.CentroCostoModule), data: { title: 'centroCosto.pnl.title' } },
                    { path: 'personal', loadChildren: () => loadRemoteModule('escalafon', './PersonalModule').then(m => m.PersonalModule), data: { title: 'personal.pnl.title' } }
                ]
            },

            // rrhhplanilla
            {
                path: 'rrhhplanilla', children: [
                    { path: 'tipoPlanilla', loadChildren: () => loadRemoteModule('planilla', './TipoPlanillaModule').then(m => m.TipoPlanillaModule), data: { title: 'tipoPlanilla.pnl.title' } },
                    { path: 'valoresUIT', loadChildren: () => loadRemoteModule('planilla', './ValoresUITModule').then(m => m.ValoresUITModule), data: { title: 'valoresuit.pnl.title' } },
                    { path: 'conceptoPdt', loadChildren: () => loadRemoteModule('planilla', './ConceptoPdtModule').then(m => m.ConceptoPdtModule), data: { title: 'conceptoPdt.pnl.title' } },
                    { path: 'conceptoRegimenPensionario', loadChildren: () => loadRemoteModule('planilla', './ConceptoRegimenPensionarioModule').then(m => m.ConceptoRegimenPensionarioModule), data: { title: 'conceptoRegimenPensionario.pnl.title' } },
                    { path: 'periodoPlanilla', loadChildren: () => loadRemoteModule('planilla', './PeriodoPlanillaModule').then(m => m.PeriodoPlanillaModule), data: { title: 'periodoPlanilla.pnl.title' } },
                    { path: 'personal', loadChildren: () => loadRemoteModule('planilla', './PersonalPlanillaModule').then(m => m.PersonalPlanillaModule), data: { title: 'personal.pnl.title' } },
                    { path: 'personalConcepto', loadChildren: () => loadRemoteModule('planilla', './PersonalConceptoModule').then(m => m.PersonalConceptoModule), data: { title: 'personalConcepto.pnl.title' } },
                    { path: 'planilla', loadChildren: () => loadRemoteModule('planilla', './PlanillaModule').then(m => m.PlanillaModule), data: { title: 'planilla.pnl.title' } },
                    { path: 'tareoPersonal', loadChildren: () => loadRemoteModule('planilla', './TareoPersonalModule').then(m => m.TareoPersonalModule), data: { title: 'tareo.pnl.title' } },
                    { path: 'epsPersonal', loadChildren: () => loadRemoteModule('planilla', './EPSPersonalModule').then(m => m.EPSPersonalModule), data: { title: 'epsPersonal.pnl.title' } },
                    { path: 'vacaciones', loadChildren: () => loadRemoteModule('planilla', './VacacionesPersonalModule').then(m => m.VacacionesPersonalModule), data: { title: 'vacaciones.pnl.title' } },
                    { path: 'epsConf', loadChildren: () => loadRemoteModule('planilla', './EPSConfModule').then(m => m.EPSConfModule), data: { title: 'epsConf.pnl.title' } },
                    { path: 'feriado', loadChildren: () => loadRemoteModule('planilla', './FeriadoModule').then(m => m.FeriadoModule), data: { title: 'feriado.pnl.title' } },
                    //{ path: 'variableConf', loadChildren: () => loadRemoteModule('planilla', './VariableConfModule').then(m => m.VariableConfModule), data: { title: 'variableConf.pnl.title' } }
                ]
            },

            // matricula
            {
                path: 'matricula', children: [
                    { path: 'grupo', loadChildren: () => loadRemoteModule('matricula', './GrupoModule').then(m => m.GrupoModule), data: { title: 'grupo.pnl.title' } }
                ]
            },
            {
                path: 'matricula', children: [
                    { path: 'pabellon', loadChildren: () => loadRemoteModule('matricula', './PabellonModule').then(m => m.PabellonModule), data: { title: 'pabellon.pnl.title' } }
                ]
            },

            // pago
            {
                path: 'pago', children: [
                    { path: 'clasificacion', loadChildren: () => loadRemoteModule('pago', './ClasificacionModule').then(m => m.ClasificacionModule), data: { title: 'clasificacion.pnl.title' } },
                    { path: 'tipoDocSunatEntidad', loadChildren: () => loadRemoteModule('pago', './TipoDocSunatEntidadModule').then(m => m.TipoDocSunatEntidadModule), data: { title: 'tipoDocSunatEntidad.pnl.title' } }
                ]
            },

            // generador
            {
                path: 'generador', children: [
                    { path: 'tecnologia', loadChildren: () => loadRemoteModule('generador', './TecnologiaModule').then(m => m.TecnologiaModule), data: { title: 'tecnologia.pnl.title' } },
                    { path: 'proyecto', loadChildren: () => loadRemoteModule('generador', './ProyectoModule').then(m => m.ProyectoModule), data: { title: 'proyecto.pnl.title' } }
                ]
            },

            // 404 & Catch all
            { path: '404-not-found', pathMatch: 'full', loadChildren: () => import('./modules/admin/pages/error/error-404/error-404.module').then(m => m.Error404Module) },
            { path: '**', redirectTo: '404-not-found' }
        ]
    }
];
