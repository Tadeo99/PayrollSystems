import { ChangeDetectionStrategy, ChangeDetectorRef, Component, OnChanges, OnDestroy, SimpleChanges, ViewChild, ViewEncapsulation } from '@angular/core';
import { UntypedFormBuilder, UntypedFormGroup } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatAccordion } from '@angular/material/expansion';
import { ActivatedRoute, Router } from '@angular/router';
import { BaseComponent } from '@ng-mf/shared/components/core';
import { User, UserService } from '@ng-mf/shared/service/aas';
import { CommonService, ListaItemType } from '@ng-mf/shared/service/comun';
import { BsConfirmationService, SelectItemVO, TypeSelectItemService } from '@ng-mf/shared/service/core';
import { TranslocoService } from '@ngneat/transloco';
import { takeUntil } from 'rxjs';
import { AsignaPostulante } from '../postulante/asignapostulante.types';
import { Sede } from '../sede/sede.types';
import { RegistrarAdmisionService } from './registraradmision.service';





/**
 * La Class RegistrarAdmisionComponent.
 * <ul>
 * <li>Copyright 2017 ndavilal -
 * ndavilal. Todos los derechos reservados.</li>
 * </ul>
 *
 * @author ndavilal
 * @version 1.0, Mon Oct 16 14:31:03 COT 2017
 * @since SIAA-CORE 2.1
 */
//https://stackoverflow.com/questions/63066986/agm-core-and-angular-google-maps-what-are-the-differences-between-them-for-goo

@Component({
	selector: 'ng-mf-bs-registraradmision',
	templateUrl: './registraradmision.component.html',
	styleUrls: ['./registraradmision.component.scss'],
	encapsulation: ViewEncapsulation.None,
	changeDetection: ChangeDetectionStrategy.OnPush
})
export class RegistrarAdmisionComponent extends BaseComponent<any> implements OnChanges, OnDestroy {

	max = 8;
	min = 0;
	showTicks = false;
	step = 1;
	thumbLabel = true;

	nroVacante = 0;
	public showRegistrar = false;
	ubigeoSede: SelectItemVO[] | undefined = [];
	departamentos: SelectItemVO[] | undefined = [];
	provincias: SelectItemVO[] | undefined = [];
	distritos: SelectItemVO[] | undefined = [];

	listaPostulante: AsignaPostulante[] = [];

	/** La lista nivel. */
	public listaNivel: SelectItemVO[] | undefined = [];
	public nivelMap: Map<any, string> = new Map<any, string>();
	public listaSede: Sede[] = [];
	public listaGradoSelectItemMap: Map<any, SelectItemVO[]> = new Map<any, SelectItemVO[]>();
	public listaPostulanteMap: Map<number, AsignaPostulante> = new Map<number, AsignaPostulante>();

	user!: User;

	public ubigeoDepartamentoMap: Map<string, string> = new Map<string, string>();
	public ubigeoProvinciaMap: Map<string, string> = new Map<string, string>();
	public ubigeoDistritoMap: Map<string, string> = new Map<string, string>();

	stepAcordion = 0;

	verticalStepperForm!: UntypedFormGroup;


	@ViewChild(MatAccordion) accordion!: MatAccordion;

	constructor(
		private service: RegistrarAdmisionService,
		private fb: UntypedFormBuilder,
		public dialog: MatDialog,
		router: Router,
		route: ActivatedRoute,
		private _changeDetectorRef: ChangeDetectorRef,
		public commonService: CommonService,
		public userService: UserService,
		public override _translocoService: TranslocoService,
		public override _bsConfirmationService: BsConfirmationService,
		public typeService: TypeSelectItemService
	) {
		super(_translocoService, _bsConfirmationService);
		this.debounceTimeProcesar().subscribe(term => {
			// this.search = term; this.buscar() 
		});
		this.SORT_NAME = 'nombre';
	}

	ngOnChanges(changes: SimpleChanges) {

		this.showAccion();
	}

	ngOnInit() {
		this.onInit();
		this.inicializar();
		this.onChange();
		this.viewPage = "registrarAdmision";
		this.userService.user$
			.pipe(takeUntil(this._unsubscribeAll))
			.subscribe((user: User) => {
				this.user = user;
				// Mark for check
				this.showRegistrar = (this.user?.id ?? '') !== '';
				this._changeDetectorRef.markForCheck();
			});
	}
	/**
	 * On destroy
	 */
	override ngOnDestroy(): void {
		// Unsubscribe from all subscriptions
		this._unsubscribeAll.next(null);
		this._unsubscribeAll.complete();
	}

	onInit() {
		// Vertical stepper form
		this.verticalStepperForm = this.fb.group({
			stepValidarVacante: this.fb.group({
				nroVacante: [0],
				departamento: ['', { updateOn: 'change' }],
				provincia: ['', { updateOn: 'change' }],
				distrito: ['', { updateOn: 'change' }]
			}),
			stepReultadoVacante: this.fb.group({
				secondCtrl: ['']
			}),
			step3: this.fb.group({
				tercerCtrl: ['']
			})
		});
	}

	public generarData() {
		try {
			if (this.service.dataMap.has("stepIndex")) {
				this.stepIndex = this.service.dataMap.get("stepIndex");
			}
			if (this.service.dataMap.has("nroVacante")) {
				this.nroVacante = this.service.dataMap.get("nroVacante");
				this.verticalStepperForm.get('stepValidarVacante.nroVacante')?.setValue(this.nroVacante);
			}
			if (this.service.dataMap.has("stepAcordion")) {
				this.stepAcordion = this.service.dataMap.get("stepAcordion");
			}
			if (this.service.dataMap.has("listaPostulante")) {
				this.listaPostulante = this.service.dataMap.get("listaPostulante");
			}
			if (this.service.dataMap.has("departamento")) {
				this.verticalStepperForm.get('stepValidarVacante.departamento')?.setValue(this.service.dataMap.get("departamento"), { onlySelf: true, emitEvent: false });
				this.verticalStepperForm.get('stepValidarVacante.provincia')?.setValue(this.service.dataMap.get("provincia"), { onlySelf: true, emitEvent: false });
				this.verticalStepperForm.get('stepValidarVacante.distrito')?.setValue(this.service.dataMap.get("distrito"));

				this.obtenerProvinciaDisponible(this.service.dataMap.get("departamento"));
				this.obtenerDistritoDisponible(this.service.dataMap.get("provincia"));
			}
			this._changeDetectorRef.markForCheck();

		} catch (e) {
			this.mostrarMensajeError(e);
		}
	}
	private onChange(): void {
		//this.debounceTimeProcesarEmail().subscribe(term =>  {this.valueChangeEmail()});
		this.verticalStepperForm?.get("stepValidarVacante.departamento")?.valueChanges.subscribe(value => {
			this.verticalStepperForm?.get("stepValidarVacante.provincia")?.setValue(null, { onlySelf: true, emitEvent: false });
			this.obtenerProvinciaDisponible(value ?? '');
			this.service.dataMap.set("departamento", value ?? '');
		});
		this.verticalStepperForm?.get("stepValidarVacante.provincia")?.valueChanges.subscribe(value => {
			this.verticalStepperForm?.get("stepValidarVacante.distrito")?.setValue(null, { onlySelf: true, emitEvent: false });
			this.obtenerDistritoDisponible(value ?? '');
			this.service.dataMap.set("provincia", value ?? '');
		});
		this.verticalStepperForm?.get("stepValidarVacante.distrito")?.valueChanges.subscribe(value => {
			this.obtenerVacantesDisponiblesByUbigeo(value ?? '');
			this.service.dataMap.set("distrito", value ?? '');
		});
	}
	/**
	 * Inicializar.
	 *
	 */
	private async inicializar() {
		//super.validarPaginaView();
		this.cargarCombo();
	}

	setStepAcordion(index: number) {
		this.stepAcordion = index;
		this.service.dataMap.set("stepAcordion", this.stepAcordion);
	}

	nextStepAcordion() {
		this.stepAcordion++;
		this.service.dataMap.set("stepAcordion", this.stepAcordion);
	}

	prevStepAcordion() {
		this.stepAcordion--;
		this.service.dataMap.set("stepAcordion", this.stepAcordion);
	}

	public onStepChange(event: any): void {
		this.setStep(event.selectedIndex);
	}

	setStep(index: number) {
		this.stepIndex = index;
	}

	nextStep() {
		this.stepIndex = this.stepIndex + 1;
	}

	prevStep() {
		this.stepIndex--;
	}
	public async cargarCombo() {
		let paramsTemp: Map<any, any> = new Map<any, any>();
		paramsTemp = paramsTemp.set(ListaItemType.T38_NIVEL, 0);
		//paramsTemp = paramsTemp.set(ListaItemType.TURNO,0);	
		await this.commonService.obtenerListaItemSelectItemMap(paramsTemp);
		this.listaNivel = this.commonService.getListaItemSelectItem(ListaItemType.T38_NIVEL);

		this.listaNivel?.forEach((data) => {
			const idItemByNivel = Number(data.id);
			if (!this.nivelMap.has(idItemByNivel)) {
				this.nivelMap.set(idItemByNivel, data.nombre);
			}
		});

		await this.service.obtenerListaGradoSelectItemMap();
		this.listaGradoSelectItemMap = this.service.listaGradoSelectItemMap;

		paramsTemp = new Map<any, any>();
		paramsTemp = paramsTemp.set("departamento", 0);
		paramsTemp = paramsTemp.set("provincia", 0);
		paramsTemp = paramsTemp.set("distrito", 0);
		await this.commonService.obtenerListaSelectItemVOMap("common", paramsTemp);
		this.commonService.generarUbigeoMap();


		paramsTemp = new Map<any, any>();
		paramsTemp = paramsTemp.set("ubigeoSede", 0);
		await this.commonService.obtenerListaSelectItemVOMap("admision", paramsTemp);
		this.ubigeoSede = this.commonService.getListaSelectVOItem("ubigeoSede");
		this.generarUbigeoDisponible();
		this.obtenerDepartamentoDisponible();

		this.generarData();
	}
	public generarUbigeoDisponible() {
		this.ubigeoDepartamentoMap = new Map<string, string>();
		this.ubigeoProvinciaMap = new Map<string, string>();
		this.ubigeoDistritoMap = new Map<string, string>();
		this.ubigeoSede?.forEach((data) => {
			if (this.commonService.ubigeoMap.has(data.id)) {
				const ubigeoProvincia = this.commonService.ubigeoMap.get(data.id) ?? '';
				const ubigeoDepartamento = this.commonService.ubigeoMap.get(ubigeoProvincia) ?? '';
				if (!this.ubigeoDepartamentoMap.has(ubigeoDepartamento)) {
					this.ubigeoDepartamentoMap.set(ubigeoDepartamento, '');
				}
				if (!this.ubigeoProvinciaMap.has(ubigeoProvincia)) {
					this.ubigeoProvinciaMap.set(ubigeoProvincia, '');
				}
				if (!this.ubigeoDistritoMap.has(data.id)) {
					this.ubigeoDistritoMap.set(data.id, '');
				}
			}
		});
	}
	public obtenerDepartamentoDisponible() {
		this.departamentos = [];
		const deparmanentoTmp = this.commonService.getListaSelectVOItem("departamento");
		deparmanentoTmp?.forEach((data) => {
			if (this.ubigeoDepartamentoMap.has(data.id)) {
				this.departamentos?.push(data);
			}
		});
	}
	public obtenerProvinciaDisponible(idUbigeo: string) {
		this.provincias = [];
		const provinciasTmp = this.commonService.generarProvincia(idUbigeo);
		provinciasTmp?.forEach((data) => {
			if (this.ubigeoProvinciaMap.has(data.id)) {
				this.provincias?.push(data);
			}
		});
	}
	public obtenerDistritoDisponible(idUbigeo: string) {
		this.distritos = [];
		const distritosTmp = this.commonService.generarDistrito(idUbigeo);
		distritosTmp?.forEach((data) => {
			if (this.ubigeoDistritoMap.has(data.id)) {
				this.distritos?.push(data);
			}
		});
	}
	public async obtenerVacantesDisponibles() {
		this.obtenerVacantesDisponiblesByUbigeo(this.verticalStepperForm?.get("stepValidarVacante.distrito")?.value ?? '');
	}
	public async obtenerVacantesDisponiblesByUbigeo(idUbigeo: string) {
		const listaPostulanteAsignadoMap: Map<string, boolean> = new Map<string, boolean>();
		const listaNombreGrado: Map<any, string> = new Map<any, string>();
		await this.service.listarVacantesDisponibles(idUbigeo).then(
			data => {
				this.listaSede = data;
				this.listaSede.forEach(element => {
					element.sedeDetSedeList.forEach(objDet => {
						const key = element.codigo + "" + element.idSede + "" + objDet.grado.idGrado;
						if (objDet.checked == true) {
							listaPostulanteAsignadoMap.set(key, objDet.checked);
						}
					});
				});
				this.listaSede.forEach(element => {
					element.sedeDetSedeList = [];
					element.sedeAsignaPostulanteList = [];
					this.listaPostulante.forEach(objDet => {
						objDet.checked = false;
						const key = element.codigo + "" + element.idSede + "" + objDet.grado.idGrado;
						const listaGradoTemp = this.listaGradoSelectItemMap.get(objDet.grado.idItemByNivel);
						listaGradoTemp?.forEach(objG => {
							listaNombreGrado.set(objG.id, objG.nombre);
						});
						const clon = Object.assign({}, objDet);
						clon.grado = Object.assign({}, objDet.grado);
						clon.grado.idItemByNivel = objDet.grado.idItemByNivel;
						clon.grado.nombre = listaNombreGrado?.get(objDet?.grado?.idGrado) ?? '';

						clon.checked = listaPostulanteAsignadoMap.has(key);
						objDet.grado.nombre = clon.grado.nombre;
						element.sedeAsignaPostulanteList.push(clon);
					});
				});
				this._changeDetectorRef.markForCheck();
			}
		);
	}

	public selectSede(sede: Sede) {
		sede.checked = true;
		this.listaSede.forEach(element => {
			element.checked = false;
			if (sede.idSede == element.idSede) {
				sede.checked = true;
				element.checked = true;
			}
		});
		this.listaPostulante.forEach(element => {
			element.sede = sede.idSede;
		});
	}

	public registrar() {
		this.showRegistrar = true;
	}

	public cancelarApoderado(event: any) {//esto se invoca desde el componente hijo cancelar
		if (event.lanzoEvento) {
			this.showRegistrar = event.showRegistrar;
		}
	}

	public iniciarSession(event: any) {//esto se invoca desde el componente hijo cancelar
		if (event.lanzoEvento) {
			this.showRegistrar = true;
		}
	}

	onInputChange(event: any) {
		this.nroVacante = event;
		this.generarPostulante();
		this.service.dataMap.set("nroVacante", this.nroVacante);
	}
	public generarPostulante() {
		let i = 1;
		this.listaPostulante.forEach(element => {
			const objPos = Object.assign({}, element)
			objPos.grado = Object.assign({}, element.grado);
			this.listaPostulanteMap.set(i, objPos);
			i++;
		});
		this.listaPostulante = [];
		i = 0;
		for (i = 1; i <= this.nroVacante; i++) {
			const objPos: AsignaPostulante = {
				grado: { 
					idItemByNivel: 0, 
					idGrado: 0, 
					itemByNivel: { 
						nombre: '' 
					}
				 },
				postulante: { 
					nombres: '' 
				},
				apoderado: '',
				anho: 0, 
				periodo: '',
				sede: ''
			};
			if (this.listaPostulanteMap.has(i)) {
				console.log("postulante existe " + i);
				console.log("postulante itemByNivel " + this.listaPostulanteMap?.get(i)?.grado?.idItemByNivel);
				console.log("postulante grado " + this.listaPostulanteMap?.get(i)?.grado?.idGrado);
				objPos.grado.idItemByNivel = this.listaPostulanteMap?.get(i)?.grado?.idItemByNivel ?? 0;
				objPos.grado.itemByNivel.nombre = this.nivelMap.get(Number(objPos.grado.idItemByNivel));
				objPos.grado.idGrado = this.listaPostulanteMap?.get(i)?.grado?.idGrado ?? 0;
			}
			objPos.postulante.nombres = 'Postulante ' + i;
			objPos.apoderado = this.user?.codigoExterno ?? '';
			//this.listaPostulante.push({name: 'Postulante ' + i, nivel: 0,grado:'',listaGrado : [] });
			this.listaPostulante.push(objPos);
		}
		this.service.dataMap.set("listaPostulante", this.listaPostulante);
	}
	public onChangeNivel($event: any, objPost: AsignaPostulante) {
		objPost.grado.idItemByNivel = $event.value;
		objPost.grado.itemByNivel.nombre = this.nivelMap.get(Number(objPost.grado.idItemByNivel));
	}
	public onChangeGrado($event: any, objPost: AsignaPostulante) {
		objPost.grado.idGrado = $event.value;
	}

	set stepIndex(_stepIndex: number) {
		// Store the value
		this.service.dataMap.set("stepIndex", _stepIndex);
	}
	get stepIndex(): number {
		return this.service.dataMap.get("stepIndex") ?? 0;
	}
}