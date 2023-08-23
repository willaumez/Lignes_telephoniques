import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GestionAttributComponent } from './gestion-attribut.component';

describe('GestionAttributComponent', () => {
  let component: GestionAttributComponent;
  let fixture: ComponentFixture<GestionAttributComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [GestionAttributComponent]
    });
    fixture = TestBed.createComponent(GestionAttributComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
