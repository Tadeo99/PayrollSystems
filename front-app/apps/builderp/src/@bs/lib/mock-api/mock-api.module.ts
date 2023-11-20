import { APP_INITIALIZER, ModuleWithProviders, NgModule } from '@angular/core';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { BS_MOCK_API_DEFAULT_DELAY } from './mock-api.constants';
import { BsMockApiInterceptor } from './mock-api.interceptor';

@NgModule({
    providers: [
        {
            provide: HTTP_INTERCEPTORS,
            useClass: BsMockApiInterceptor,
            multi: true
        }
    ]
})
export class BsMockApiModule {
    /**
     * BsMockApi module default configuration.
     *
     * @param mockApiServices - Array of services that register mock API handlers
     * @param config - Configuration options
     * @param config.delay - Default delay value in milliseconds to apply all responses
     */
    static forRoot(mockApiServices: any[], config?: { delay?: number }): ModuleWithProviders<BsMockApiModule> {
        return {
            ngModule: BsMockApiModule,
            providers: [
                {
                    provide: APP_INITIALIZER,
                    deps: [...mockApiServices],
                    useFactory: () => (): any => null,
                    multi: true
                },
                {
                    provide: BS_MOCK_API_DEFAULT_DELAY,
                    useValue: config?.delay ?? 0
                }
            ]
        };
    }
}
