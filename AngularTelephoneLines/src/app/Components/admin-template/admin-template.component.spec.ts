import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminTemplateComponent } from './admin-template.component';
import {HttpClientModule} from "@angular/common/http";

describe('AdminTemplateComponent', () => {
  let component: AdminTemplateComponent;
  let fixture: ComponentFixture<AdminTemplateComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule],
      declarations: [AdminTemplateComponent]
    });
    fixture = TestBed.createComponent(AdminTemplateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
