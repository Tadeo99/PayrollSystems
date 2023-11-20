import { Injectable } from '@angular/core';
import { BsDrawerComponent } from './drawer.component';

@Injectable({
    providedIn: 'root'
})
export class BsDrawerService {
    private _componentRegistry: Map<string, BsDrawerComponent> = new Map<string, BsDrawerComponent>();

    /**
     * Constructor
     */
    constructor() {
        //
    }

    // -----------------------------------------------------------------------------------------------------
    // @ Public methods
    // -----------------------------------------------------------------------------------------------------

    /**
     * Register drawer component
     *
     * @param name
     * @param component
     */
    registerComponent(name: string, component: BsDrawerComponent): void {
        this._componentRegistry.set(name, component);
    }

    /**
     * Deregister drawer component
     *
     * @param name
     */
    deregisterComponent(name: string): void {
        this._componentRegistry.delete(name);
    }

    /**
     * Get drawer component from the registry
     *
     * @param name
     */
    getComponent(name: string): BsDrawerComponent | undefined {
        return this._componentRegistry.get(name);
    }
}
