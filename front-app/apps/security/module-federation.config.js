module.exports = {
  name: 'security',
  exposes: {
    './Module': 'apps/security/src/app/remote-entry/entry.module.ts',
    './TipoUsuarioModule': 'apps/security/src/app/tipousuario/tipousuario.module.ts',
    './GrupoUsuarioModule': 'apps/security/src/app/grupousuario/grupousuario.module.ts',
    './SistemaModule': 'apps/security/src/app/sistema/sistema.module.ts',
    './PropertiesModule': 'apps/security/src/app/properties/properties.module.ts',
    './PrivilegioModule': 'apps/security/src/app/privilegio/privilegio.module.ts',
    './EntidadModule': 'apps/security/src/app/entidad/entidad.module.ts',
    './UsuarioModule': 'apps/security/src/app/usuario/usuario.module.ts',
    './MenuModule': 'apps/security/src/app/menu/menu.module.ts',
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
