import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';
import { AppModule } from './app/app.module';
import {provideAnimations} from '@angular/platform-browser/animations';

platformBrowserDynamic().bootstrapModule(AppModule, {
  ngZoneEventCoalescing: true,
  providers: [provideAnimations()]
})
  .catch(err => console.error(err));
