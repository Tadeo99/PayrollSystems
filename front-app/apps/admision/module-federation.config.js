module.exports = {
  name: 'admision',
  exposes: {
    './Module': 'apps/admision/src/app/remote-entry/entry.module.ts',
    './GradoModule': 'apps/admision/src/app/grado/grado.module.ts',
    './SeccionModule': 'apps/admision/src/app/seccion/seccion.module.ts',
    './SedeModule': 'apps/admision/src/app/sede/sede.module.ts',
    './PostulanteModule': 'apps/admision/src/app/postulante/postulante.module.ts',
    './ApoderadoModule': 'apps/admision/src/app/apoderado/apoderado.module.ts',
    './NotesModule': 'apps/admision/src/app/notes/notes.module.ts',
    './RegistrarAdmisionModule': 'apps/admision/src/app/registraradmision/registraradmision.module.ts',
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
