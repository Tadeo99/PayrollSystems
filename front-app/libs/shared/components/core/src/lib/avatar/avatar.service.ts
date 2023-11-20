/**
 * La Class FileUploaderService.
 * <ul>
 * <li>Copyright 2017 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Thu May 18 12:31:05 COT 2017
 * @since SIAA-CORE 2.1
 */
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { BaseService } from '@ng-mf/shared/service/core';

@Injectable()
export class FileUploaderService extends BaseService<any> {

  constructor(http: HttpClient) { 
    super(http);
    this.url = "common/fileUpload";
  }

  public subirArchivoAlumno( file : any) : Observable<any> {
    return this.post(this.url + "/alumno",file);
  }

  public obtenerArchivoAlumno( fotoImg : any) : Observable<any> {
    return this.get(this.url + "/alumno/" + fotoImg);
  }

  public subirArchivoPersonal( file : any) : Observable<any> {
    return this.post(this.url + "/personal",file);
  }

  public obtenerArchivoPersonal( fotoImg : any) : Observable<any> {
    return this.get(this.url + "/personal/" + fotoImg);
  }
  
}
